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

   cloudfront_ordered_cache_behaviors = [
      {
         allowed_methods          = ["DELETE", "GET", "HEAD", "OPTIONS", "PATCH", "POST", "PUT"]
         cached_methods           = ["GET", "HEAD", "OPTIONS"]
         cache_policy_id          = data.aws_cloudfront_cache_policy.index_cache_policy.id
         origin_request_policy_id = data.aws_cloudfront_origin_request_policy.origin_request_policy.id
         path_pattern             = "/index.html"
         target_origin_id         = "kitchen-pantry.com"
         viewer_protocol_policy   = "redirect-to-https"
      }
   ]
}

data "aws_cloudfront_cache_policy" "index_cache_policy" {
   name = "Managed-CachingDisabled"
}

data "aws_cloudfront_origin_request_policy" "origin_request_policy" {
   name = "Managed-CORS-S3Origin"
}
