using Microsoft.EntityFrameworkCore;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Business.Product
{
    public class ProductService(IUnitOfWork unitOfWork) : IProductService
    {
        public async Task<IEnumerable<Repository.Entities.Product>> GetAllProducts()
        {
            var products = unitOfWork
                .GenericRepository<Repository.Entities.Product>()
                .GetAll()
                .Include(x => x.Category);
            return await products.ToListAsync();
        }

        public async Task<Repository.Entities.Product?> GetProductById(int productId)
        {
            var product = await unitOfWork
                .GenericRepository<Repository.Entities.Product>()
                .GetAll()
                .Include(x => x.Category)
                .FirstOrDefaultAsync(x => x.ProductId == productId);

            return product;
        }
    }
}
