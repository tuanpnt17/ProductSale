using ProductSale.Business.Models;
using ProductSale.Repository.Helpers;

namespace ProductSale.Business.Product
{
    public interface IProductService
    {
        Task<Pagination<Repository.Entities.Product>> GetProductsAsync(ProductQueryDto query);
        public Task<IEnumerable<Repository.Entities.Product>> GetAllProducts();

        public Task<Repository.Entities.Product?> GetProductById(int productId);
    }
}
