using Microsoft.EntityFrameworkCore;
using ProductSale.Business.Models;
using ProductSale.Repository.Helpers;
using ProductSale.Repository.Interfaces;
using System.Linq.Expressions;

namespace ProductSale.Business.Product
{
    public class ProductService(IUnitOfWork unitOfWork) : IProductService
    {
        public async Task<Pagination<Repository.Entities.Product>> GetProductsAsync(
            ProductQueryDto query
        )
        {
            if (query.PageIndex < 1)
                query.PageIndex = 1;
            if (query.PageSize < 1)
                query.PageSize = 10;

            // Build the predicate for filtering
            Expression<Func<Repository.Entities.Product, bool>> predicate = p => true; // Default: no filter
            if (
                !string.IsNullOrEmpty(query.Search)
                || query.MinPrice.HasValue
                || query.MaxPrice.HasValue
                || (query.CategoryIds != null && query.CategoryIds.Any())
            )
            {
                predicate = p =>
                    (
                        string.IsNullOrEmpty(query.Search)
                        || p.ProductName.ToLower().Contains(query.Search.ToLower())
                    )
                    && (!query.MinPrice.HasValue || p.Price >= query.MinPrice.Value)
                    && (!query.MaxPrice.HasValue || p.Price <= query.MaxPrice.Value)
                    && (
                        query.CategoryIds == null
                        || !query.CategoryIds.Any()
                        || query.CategoryIds.Contains(p.CategoryId ?? -1)
                    );
            }

            // Determine sorting
            Expression<Func<Repository.Entities.Product, object>>? orderBy = null;
            if (!string.IsNullOrEmpty(query.SortBy))
            {
                switch (query.SortBy.ToLower())
                {
                    case "price":
                        orderBy = p => p.Price;
                        break;
                    default:
                        orderBy = p => p.ProductId;
                        break;
                }
            }
            else
            {
                orderBy = p => p.ProductId;
            }

            return await unitOfWork
                .GenericRepository<Repository.Entities.Product>()
                .GetPaginationAsync(
                    predicate: predicate,
                    //includeProperties: "Category",
                    pageIndex: query.PageIndex,
                    pageSize: query.PageSize,
                    orderBy: orderBy,
                    isDescending: query.SortDescending
                );
        }

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
