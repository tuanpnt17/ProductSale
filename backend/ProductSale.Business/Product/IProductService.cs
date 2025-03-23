namespace ProductSale.Business.Product
{
    public interface IProductService
    {
        public Task<IEnumerable<Repository.Entities.Product>> GetAllProducts();

        public Task<Repository.Entities.Product?> GetProductById(int productId);
    }
}
