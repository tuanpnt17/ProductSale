namespace ProductSale.Business.Models
{
    public class ProductQueryDto
    {
        public int PageIndex { get; set; } = 1;
        public int PageSize { get; set; } = 10;

        // Search options
        public string? Search { get; set; }

        // Sorting options
        public string? SortBy { get; set; } // "price"
        public bool SortDescending { get; set; } = false; // Default to ascending

        // Filtering options
        public decimal? MinPrice { get; set; }
        public decimal? MaxPrice { get; set; }
        public List<int>? CategoryIds { get; set; }
    }
}
