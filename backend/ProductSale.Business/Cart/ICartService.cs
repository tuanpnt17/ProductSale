namespace ProductSale.Business.Cart
{
    public interface ICartService
    {
        public Task<Repository.Entities.Cart?> GetCart(int userId);
        public Task AddToCart(int userId, int productId, int quantity);
    }
}
