using ProductSale.Business.Models;
using ProductSale.Repository.Helpers;

namespace ProductSale.Business.Product
{
    public interface IProductService
    {
        Task<Pagination<ProductSummaryDto>> GetProductsAsync(ProductQueryDto query);
    }
}
