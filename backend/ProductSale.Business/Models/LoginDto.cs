using System.ComponentModel.DataAnnotations;

namespace ProductSale.Business.Models
{
    public class LoginDto
    {
        [Required]
        public required string Username { get; set; }

        [Required]
        public required string Password { get; set; }
    }
}
