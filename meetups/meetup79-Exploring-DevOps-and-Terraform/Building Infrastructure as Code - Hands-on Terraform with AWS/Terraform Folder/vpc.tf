module "vpc" {
  source = "terraform-aws-modules/vpc/aws"

  name = var.vpc_name
  cidr = var.cidr_block

  azs             = ["eu-west-1a", "eu-west-1b", "eu-west-1c"] # REPLACE AZs OF REGION USED
  private_subnets = ["10.10.10.0/26"]
  public_subnets  = ["10.10.10.64/26"]

  enable_nat_gateway = false
  enable_vpn_gateway = false

  map_public_ip_on_launch = true

  tags = {
    Product = var.tag_product
    Environment = var.tag_environment
    Orchestration = var.tag_orchestration
    Description = var.tag_description
  }
}

module "ec2_instance" {
  source  = "terraform-aws-modules/ec2-instance/aws"

  name = "petousis-webser"

  instance_type          = "t2.micro"
  key_name               = "REPLACE-WITH-PEM-FILE-NAME"
  monitoring             = false
  vpc_security_group_ids = [aws_security_group.allow_tls.id]
  subnet_id              = module.vpc.public_subnets[0]

  user_data = <<-EOT
    #!/bin/bash
    yum update -y
    yum install -y httpd.x86_64
    systemctl start httpd.service
    systemctl enable httpd.service
    echo “Hello World from PETOUSIS” > /var/www/html/index.html
  EOT


  tags = {
    Product = var.tag_product
    Environment = var.tag_environment
    Orchestration = var.tag_orchestration
    Description = var.tag_description
  }
}

resource "aws_security_group" "allow_tls" {
  name        = "allow_tls"
  description = "Allow TLS inbound traffic"
  vpc_id      = module.vpc.vpc_id

  ingress {
    description      = "TLS from VPC"
    from_port        = 443
    to_port          = 443
    protocol         = "tcp"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  ingress {
    description      = "HTTP from VPC"
    from_port        = 80
    to_port          = 80
    protocol         = "tcp"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  tags = {
    Name = "allow_tls"
    Product = var.tag_product
    Environment = var.tag_environment
    Orchestration = var.tag_orchestration
    Description = var.tag_description
  }
}


####################################################################
# RESOURCES WE CREATED AND DECOMMISSIONED IN FAVOR OF USING MODULES #
####################################################################
# resource "aws_vpc" "main-petousis" {
#   cidr_block           = var.cidr_block
#   enable_dns_hostnames = true

#   tags = {
#     Name          = var.vpc_name
#     Product       = var.tag_product
#     Environment   = var.tag_environment
#     Contact       = var.tag_contact
#     Orchestration = var.tag_orchestration
#   }
# }

# resource "aws_subnet" "public-subnet" {
#   vpc_id     = aws_vpc.main-petousis.id
#   cidr_block = "10.10.10.0/26"

#   map_public_ip_on_launch = true

#   tags = {
#     Name = "public-subnet"
#     Product       = var.tag_product
#     Environment   = var.tag_environment
#     Contact       = var.tag_contact
#     Orchestration = var.tag_orchestration
#   }
# }

# resource "aws_subnet" "private-subnet" {
#   vpc_id     = aws_vpc.main-petousis.id
#   cidr_block = "10.10.10.64/26"

#   tags = {
#     Name = "private-subnet"
#     Product       = var.tag_product
#     Environment   = var.tag_environment
#     Contact       = var.tag_contact
#     Orchestration = var.tag_orchestration
#   }
# }