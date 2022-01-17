terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.27"
    }
  }
  required_version = ">= 0.14.9"
}

provider "aws" {
  profile                 = "default"
  region                  = "us-east-2"
  shared_credentials_file = "./credential"
}

data "template_file" "user_data" {
  template = file("ssh.yaml")
}

resource "aws_instance" "app_server" {
  ami           = "ami-0d97ef13c06b05a19"
  instance_type = "t2.micro"
  user_data = data.template_file.user_data.rendered
  key_name = "keysam"
  vpc_security_group_ids = [ aws_security_group.sg_default.id ]
  associate_public_ip_address = true
  tags = {
    Name   = "app"
    owner  = "Sam"
    groups = "app"
  }
}

resource "aws_security_group" "sg_default" {
  name = "sg_default"
  ingress {
    from_port = 22
    protocol  = "tcp"
    to_port   = 22
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    from_port = 0
    protocol  = "-1"
    to_port   = 0
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port = 8080
    protocol  = "tcp"
    to_port   = 8080
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_key_pair" "deployer" {
  key_name = "keysam"
  public_key = file("./id_rsa.pub")
}

output "instance_public_ip" {
  value       = aws_instance.app_server.*.public_ip
}

output "instance_id" {
  value       = aws_instance.app_server.*.id
}

output userdata {
  value = "\n${data.template_file.user_data.rendered}"
}