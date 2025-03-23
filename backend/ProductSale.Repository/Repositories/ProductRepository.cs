using Microsoft.EntityFrameworkCore;
using ProductSale.Repository.Data;
using ProductSale.Repository.Entities;
using ProductSale.Repository.Helpers;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Repository.Repositories;

public class ProductRepository(ApplicationDbContext context)
    : GenericRepository<Product>(context),
        IProductRepository
{
    public async Task<Pagination<Product>> GetProductsAsync(ProductQueryParams query)
    {
        var productsQuery = context.Products.AsQueryable();

        // Apply filters
        if (query.MinPrice.HasValue)
            productsQuery = productsQuery.Where(p => p.Price >= query.MinPrice.Value);

        if (query.MaxPrice.HasValue)
            productsQuery = productsQuery.Where(p => p.Price <= query.MaxPrice.Value);

        if (query.CategoryId.HasValue)
            productsQuery = productsQuery.Where(p => p.CategoryId == query.CategoryId.Value);

        // Apply sorting
        if (!string.IsNullOrEmpty(query.SortBy))
        {
            switch (query.SortBy.ToLower())
            {
                case "price":
                    productsQuery = query.SortDescending
                        ? productsQuery.OrderByDescending(p => p.Price)
                        : productsQuery.OrderBy(p => p.Price);
                    break;
                case "category":
                    productsQuery = query.SortDescending
                        ? productsQuery.OrderByDescending(p => p.Category.CategoryName)
                        : productsQuery.OrderBy(p => p.Category.CategoryName);
                    break;
                default:
                    productsQuery = productsQuery.OrderBy(p => p.ProductId);
                    break;
            }
        }
        else
        {
            productsQuery = productsQuery.OrderBy(p => p.ProductId);
        }

        // Get total count
        var totalItemsCount = await productsQuery.CountAsync();
        var items = await productsQuery
            .Skip((query.PageIndex - 1) * query.PageSize)
            .Take(query.PageSize)
            .ToListAsync();

        return new Pagination<Product>
        {
            TotalItemsCount = totalItemsCount,
            PageSize = query.PageSize,
            PageIndex = query.PageIndex,
            Items = items,
        };
    }
}
