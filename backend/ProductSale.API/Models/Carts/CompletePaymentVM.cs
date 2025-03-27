namespace ProductSale.API.Models.Carts
{
	public class CompletePaymentVM
	{
		public int UserId { get; set; }
		public string PaymentMethod { get; set; }
		public string  BillingAddress { get; set; }
	}
}
