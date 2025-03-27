namespace ProductSale.Business.Models
{
    public class UserResponseDto
    {
        public int UserId { get; set; }

        public required string Username { get; set; }

        public required string Email { get; set; }

        public string? PhoneNumber { get; set; }

        public string? Address { get; set; }

        public string Role { get; set; } = null!;
    }
}
