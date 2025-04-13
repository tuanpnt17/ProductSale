# 🛍️ Android Application for Product Sale

This Android application enables users to browse a catalog of products, add them to a cart, and complete purchases using secure payment methods. It connects to a remote database through a RESTful API and offers a clean and user-friendly interface to enhance the shopping experience.

---

## 📱 Features Overview

### 1. 🔐 Authentication (10%) — *Pham Xuan Hoang*
- **Sign Up:** Users can register with a username, password, and optional details like email, phone number, and address.
- **Login:** Secure user authentication using stored credentials.
- **Password Security:** Passwords are hashed to ensure safe storage and prevent unauthorized access.

### 2. 🛒 List of Products (15%) — *Pham Xuan Hoang*
- **Product Fetching:** Retrieve product information from a remote database via RESTful API.
- **Product Display:** View name, image, price, and a short description.
- **Sorting/Filtering:** Sort by price, popularity, or category; filter by brand, price range, or ratings.

### 3. 🔍 Product Details (15%) — *Pham Nguyen Trong Tuan*
- **Detailed View:** Display full product descriptions, specs, and multiple images.
- **Add to Cart:** Add selected items with desired quantities directly from the product screen.

### 4. 🧺 Product Cart (15%) — *Tran Minh Phuc*
- **Cart Overview:** Show all items in the cart with image, name, quantity, and pricing.
- **Cart Management:** Edit quantities, remove items, or clear cart entirely.
- **Cart Total:** Dynamic updates to total cost as the cart changes.

### 5. 💳 Billing (10%) — *Tran Minh Phuc*
- **Payment Integration:** Connect with gateways like VNPay, ZaloPay, or PayPal.
- **Billing Info:** Input for payment method, billing, and shipping details.
- **Order Confirmation:** Display payment success status and summary of the order.

### 6. 🔔 Notifications (Cart Badge) (15%) — *Doan Manh Hung*
- **Cart Badge:** Display a badge with the number of items in the cart.
- **Notification Handling:** Use `NotificationCompat` to manage in-app and system notifications.

### 7. 🗺️ Map Screen (10%) — *Pham Nguyen Trong Tuan*
- **Store Location:** Integration with Google Maps to display the store’s physical location.
- **Directions:** Allow users to get real-time directions from their location to the store.

### 8. 💬 Chat Screen (10%) — *Doan Manh Hung*
- **Real-Time Chat:** Enable users to communicate with store representatives.
- **Chat API:** Use Firebase or custom API for live communication.

---

## 🧩 Technologies Used

- **Frontend:** Android (Java/Kotlin)
- **Backend:** ASP.NET with RESTful APIs
- **Database:** MySQL or SQL Server
- **Third-Party APIs:** Google Maps, Firebase, VNPay, ZaloPay, PayPal

---

## 👥 Contributors

| Name                     | Role                    |
|--------------------------|-------------------------|
| **Pham Nguyen Trong Tuan** | Team Leader & Developer (Product Details, Map) |
| **Pham Xuan Hoang**      | Developer (Authentication, Product List) |
| **Doan Manh Hung**       | Developer (Notifications, Chat) |
| **Tran Minh Phuc**       | Developer (Cart, Billing) |

---


## 📄 License

This project is licensed under the MIT License. Feel free to use and modify it as needed.

---

## 🙌 Acknowledgements

Special thanks to all contributors for their hard work and collaboration!
