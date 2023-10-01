module "s3-static-website" {
   source  = "cn-terraform/s3-static-website/aws"
   version = "1.0.8"

   providers = {
      aws.main         = aws
      aws.acm_provider = aws.acm
   }

   name_prefix                    = "kitchen-pantry"
   website_domain_name            = "kitchen-pantry.com"
   create_acm_certificate         = true
   create_route53_hosted_zone     = false
   route53_hosted_zone_id         = aws_route53_zone.kitchen_pantry_zone.id
   create_route53_website_records = true
}
