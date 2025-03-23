using AutoMapper;
using ProductSale.Business.Models;
using ProductSale.Repository.Helpers;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Business.Product
{
    public class ProductService(IProductRepository productRepository, IMapper mapper)
        : IProductService
    {
        public async Task<Pagination<ProductSummaryDto>> GetProductsAsync(ProductQueryDto query)
        {
            if (query.PageIndex < 1)
                query.PageIndex = 1;
            if (query.PageSize < 1)
                query.PageSize = 10;

            var queryParams = mapper.Map<ProductQueryParams>(query);
            var products = await productRepository.GetProductsAsync(queryParams);
            return new Pagination<ProductSummaryDto>()
            {
                TotalItemsCount = products.TotalItemsCount,
                PageSize = products.PageSize,
                PageIndex = products.PageIndex,
                Items = mapper.Map<ICollection<ProductSummaryDto>>(products.Items),
            };
        }
    }
}
