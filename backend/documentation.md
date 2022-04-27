# APIs
## Login and Registration
**For registering new user**

requires name, email and password
```js 
POST api/auth/register
```
_returns error with status 400 on failure, and on success an email is sent to the newly registerd email for confirmation with a url_

**For logging in**

requires email and password
```js
POST api/auth/login
```
_returns error with status 400 on failure, and on success returns user_id of the logged in user_

## Profile
**Update profile**

requires all the updates with user_id as param
```js
POST api/auth/update/:id
```
_returns error with status 404 on failure, and on success returns "successfully updated" with status 200_

**View Profile**

requires user_id as a param
```js
GET api/auth/view/:id
```
_returns "user not found" with status 404 on failure, and on suceess returns complete profile of the user_

## Managing products
**Add new Product**

requires product details name, description, price, seller, categories(Array), tags(Array), image(url)... and sellerId (user_id) in param
```js
POST api/product/add/:sid
```
_returns error with status 404 on failure, and on success returns "added successfully" with status 200_

**Update a product**

requires all the updates with sellerId and productId in url params (only seller(product owner) can edit the product)
```js
POST api/product/update/:sid/:pid
```
_returns error with status 404 on failure, and on success returns "updated successfully" with status 200_

**Remove a product**

requires sellerId and productId as url params (only seller(owner) can delete the product)
```js
GET api/product/remove/:sid/:pid
```
_returns "product not found" with status 404 on failure, and on success returns "removed successfully" with status 200_

**View Products offered by a particular seller**

requires sellerId in url params
```js
GET api/product/view/:sid
```
(optional) We can add query string such as catergories array and tags array to get filtered result like->
```js
GET api/product/view/:sid/?categories[]=fancy&categories[]=traditional&tags[]=easywear&tags[]=topRated
```
_returns an array of products on success otherwise error with status 404_

**View all products**

```js
GET api/product/viewall
```
We can also add query strings in url.
_returns an array of products on success_

**View a particular product**

requires product_id in url params
```js
GET api/product/viewone/:pid
```
_returns a product on success, and "not found" with status 404 on failure_

## Shopping

**Adding to cart**

requires user_id and product_id in url params
```js
GET api/shopping/cart/add/:uid/:pid
```
_if product is already in cart, returns "product is already in cart"
if user is not found return "user not found" with status 404
returns "added successfully upon success_

**view the cart**

requires user_id in url param
```js
GET api/shopping/cart/view/:uid
```
_returns an array of product ids which belong to the cart_


**Removing from cart**

requires user_id and product_id to be removed in url params
```js
GET api/shopping/cart/remove/:uid/:pid
```
_returns error with status 404 on failure, and "updated successfully" upon success_

