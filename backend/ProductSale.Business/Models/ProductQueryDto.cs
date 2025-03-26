namespace ProductSale.Business.Models
{
    public class ProductQueryDto
    {
        public int PageIndex { get; set; } = 1;
        public int PageSize { get; set; } = 10;

        // Sorting options
        public string? SortBy { get; set; } // "price", "category", "popularity"?
        public bool SortDescending { get; set; } = false; // Default to ascending

        // Filtering options
        //public string? Brand { get; set; }
        public decimal? MinPrice { get; set; }
        public decimal? MaxPrice { get; set; }

        //public double? MinRating { get; set; }
        public int? CategoryId { get; set; }
    }
}
