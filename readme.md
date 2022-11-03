## Running the app

```
./gradlew bootRun
```

## Running tests
```
./gradlew test
```


# Endpoints

## Users

To get a list of all users

GET  
`http://localhost:8080/api/users`

---

To add a user

POST  
`http://localhost:8080/api/users`

Body

```
{
    "userName": String,
    "email": String,
    "role": ENUM UserRoles
}
```

---

To delete a user

DELETE  
`http://localhost:8080/api/users/{id}`


## Product

To get a list of all products

GET  
`http://localhost:8080/api/products`

---

To add a product

POST  
`http://localhost:8080/api/products`

Body

```
{
    "code": string,
    "name": string,
    "imageURL": string,
    "price": int,
    "description": string;
}
```

Header

```
{
    userId: {id} // a user with admin access
}
```

---

To delete a Product

DELETE  
`http://localhost:8080/api/products/{id}`

Header

```
{
    userId: {id} // a user with admin access
}
```

---

To apply a discount to a product

POST  
`http://localhost:8080/api/products/{id}`

Body

```
{
    "discountType" : ENUM DiscountType,
    "discountAmount" : double,
    "discountAmountUnit" : ENUM DiscountAmountUnit,
    "purchaseUnit" : int,
    "purchaseAmount" : double,
    "unitWithDiscount" : int,
    "description" : string
}
```

Header

```
{
    userId: {id} // a user with admin access
}
```

## Cart

To get the cart for a user

GET  
`http://localhost:8080/api/carts?userId={id}`

---

To add and remove products in carts and update product quantity in cart

POST  
`http://localhost:8080/api/carts?userId={userId}&productId={productId}&quantity={quantity}`
