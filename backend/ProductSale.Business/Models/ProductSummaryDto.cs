namespace ProductSale.Business.Models
{
    public class ProductSummaryDto
    {
        public int ProductId { get; set; }
        public required string ProductName { get; set; }
        public string? BriefDescription { get; set; }
        public decimal Price { get; set; }
        public string? ImageUrl { get; set; }
    }
}
