using ProductSale.Repository.Entities;
using ProductSale.Repository.Helpers;

namespace ProductSale.Repository.Interfaces;

public interface IProductRepository : IGenericRepository<Product>
{
    Task<Pagination<Product>> GetProductsAsync(ProductQueryParams query);
}
