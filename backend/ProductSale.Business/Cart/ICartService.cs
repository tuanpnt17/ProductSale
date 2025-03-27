namespace ProductSale.Business.Cart
{
    public interface ICartService
    {
        public Task<Repository.Entities.Cart?> GetCart(int userId);
        public Task AddToCart(int userId, int productId, int quantity);
        public Task RemoveItemFromCart(int userId, int productId);
        public Task UpdateCartItemQuantity(int userId, int productId, int quantity);
        public Task ClearCart(int userId);
        public Task<decimal> GetCartTotal(int userId);
		public Task CompletePaymentAndConvertCartToOrder(int userId, string paymentMethod, string billingAddress);
	}
}
