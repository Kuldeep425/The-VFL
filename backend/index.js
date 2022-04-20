const express = require("express");
const app = express();
require("dotenv").config();
const mongoose = require("mongoose");
const PORT = process.env.PORT || 8000;
const MONGO_URI = process.env.MONGO_URI;
const AuthRoute = require("./routes/AuthRoute");

mongoose.connect(MONGO_URI).then(
  () => {
    console.log("connected to database..");
  },
  (err) => {
    console.log(err);
  }
);

app.use(express.json());
app.get("/", (req, res) => res.send("server is up and running..."));
app.use("/api/auth", AuthRoute);
app.listen(PORT, () => console.log(`server started at ${PORT}`));
