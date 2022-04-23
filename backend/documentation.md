# APIs
## Login and Registration
**For registering new user**

requires name, email and password
```js 
POST api/auth/register
```

**For logging in**

requires email and password
```js
POST api/auth/login
```

## Profile
**Update profile**

requires all the updates with user_id as param
```js
POST api/auth/update/:id
```
**View Profile**

requires user_id as a param
```js
GET api/auth/view/:id
```

## Managing products
**Add new Product**

requires product details name, description, price, seller, categories(Array), tags(Array), image(url)... and sellerId (user_id) in param
```js
POST api/product/add/:sid
```

**Update a product**

requires all the updates with sellerId and productId in url params (only seller(product owner) can edit the product)
```js
POST api/product/update/:sid/:pid
```

**Remove a product**

requires sellerId and productId as url params (only seller(owner) can delete the product)
```js
GET api/product/remove/:sid/:pid
```

**View Products offered by a particular seller**

requires sellerId in url params
```js
GET api/product/view/:sid
```
(optional) We can add query string such as catergories array and tags array to get filtered result like->
```js
GET api/product/view/:sid/?categories[]=fancy&categories[]=traditional&tags[]=easywear&tags[]=topRated
```

**View all products**

```js
GET api/product/viewall
```
We can also add query strings in url.

**View a particular product**

requires product_id in url params
```js
GET api/product/viewone/:pid
```

## Shopping

**Adding to cart**

requires user_id and product_id in url params
```js
GET api/shopping/cart/add/:uid/:pid
```

**view the cart**

requires user_id in url param
```js
GET api/shopping/cart/view/:uid
```

**Removing from cart**

requires user_id and product_id to be removed in url params
```js
GET api/shopping/cart/remove/:uid/:pid
```


