package com.example.mycommerce.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ecommerce_items")
data class ECommerceItem(
    @PrimaryKey val id: String,
    val imageUrl: String,
    val itemName: String,
    val itemPrice: Double,
    val itemRating: Float,
    val itemDescription: String
)

val eCommerceItemsList = listOf(
    ECommerceItem(
        id = "22e16db7-c051-448e-9aa2-9647b60c69d7",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Wireless Mouse",
        itemPrice = 29.99,
        itemRating = 4.5f,
        itemDescription = "Ergonomic wireless mouse with fast scrolling."
    ), ECommerceItem(
        id = "c589fc73-a0ed-4d1f-935b-3c241cac0070",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Bluetooth Speaker",
        itemPrice = 49.99,
        itemRating = 4.7f,
        itemDescription = "Portable Bluetooth speaker with powerful sound."
    ), ECommerceItem(
        id = "c5325df0-2400-4c13-a542-e8f05bce172c",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Smart Watch",
        itemPrice = 199.99,
        itemRating = 4.3f,
        itemDescription = "Stylish smart watch with health tracking features."
    ), ECommerceItem(
        id = "d28929f2-c070-48d9-83b8-78728acfb993",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Laptop Stand",
        itemPrice = 39.99,
        itemRating = 4.6f,
        itemDescription = "Adjustable laptop stand for better ergonomics."
    ), ECommerceItem(
        id = "a93056e8-33df-4f96-bc50-2fc19fa79760",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "USB-C Hub",
        itemPrice = 24.99,
        itemRating = 4.2f,
        itemDescription = "Multi-port USB-C hub with HDMI and Ethernet."
    ), ECommerceItem(
        id = "e62d16e0-8b43-4f11-8e90-08ed72531e37",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Noise Cancelling Headphones",
        itemPrice = 149.99,
        itemRating = 4.8f,
        itemDescription = "Over-ear headphones with active noise cancellation."
    ), ECommerceItem(
        id = "34268225-46e0-42ab-badb-552dbeb12c27",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Smart Home Hub",
        itemPrice = 89.99,
        itemRating = 4.4f,
        itemDescription = "Control your smart home devices with ease."
    ), ECommerceItem(
        id = "4c917556-42f1-454b-b6d1-d13019e273df",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Electric Kettle",
        itemPrice = 29.99,
        itemRating = 4.5f,
        itemDescription = "Fast boiling electric kettle with auto shut-off."
    ), ECommerceItem(
        id = "91b6d0fc-44e2-413b-94e5-87a4eeb5b4db",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Fitness Tracker",
        itemPrice = 59.99,
        itemRating = 4.3f,
        itemDescription = "Track your daily activity and workouts."
    ), ECommerceItem(
        id = "e3df1eab-1931-4520-b3e2-85b71dee7d29",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Portable Charger",
        itemPrice = 19.99,
        itemRating = 4.6f,
        itemDescription = "Compact portable charger with high capacity."
    ), ECommerceItem(
        id = "37bd1c91-361e-49fe-8c0d-6c386cdea524",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "LED Desk Lamp",
        itemPrice = 34.99,
        itemRating = 4.4f,
        itemDescription = "Adjustable LED desk lamp with touch control."
    ), ECommerceItem(
        id = "5bcd8067-4f2a-4a27-ac9e-966302bb5578",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Wireless Earbuds",
        itemPrice = 79.99,
        itemRating = 4.5f,
        itemDescription = "True wireless earbuds with long battery life."
    ), ECommerceItem(
        id = "11d001a0-ccd4-43d9-9c29-e1822c20c875",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Gaming Keyboard",
        itemPrice = 99.99,
        itemRating = 4.7f,
        itemDescription = "Mechanical gaming keyboard with RGB lighting."
    ), ECommerceItem(
        id = "b5847331-e39f-4208-be2d-e235667babdf",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Smart Thermostat",
        itemPrice = 129.99,
        itemRating = 4.4f,
        itemDescription = "Energy-saving smart thermostat with app control."
    ), ECommerceItem(
        id = "d07874b6-8fd0-4275-b0fb-0c34064ac173",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Instant Pot",
        itemPrice = 89.99,
        itemRating = 4.8f,
        itemDescription = "Multi-functional pressure cooker and slow cooker."
    ), ECommerceItem(
        id = "630138ce-c50a-4970-8e7c-431c00c9ed7c",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Electric Toothbrush",
        itemPrice = 49.99,
        itemRating = 4.5f,
        itemDescription = "Rechargeable electric toothbrush with timer."
    ), ECommerceItem(
        id = "a825bc22-a6c4-4925-b242-8f666088d187",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Air Purifier",
        itemPrice = 129.99,
        itemRating = 4.6f,
        itemDescription = "HEPA air purifier for clean and fresh air."
    ), ECommerceItem(
        id = "f8992567-8dc6-4993-99ed-a0f0d7734fd4",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Robot Vacuum",
        itemPrice = 299.99,
        itemRating = 4.4f,
        itemDescription = "Smart robot vacuum with app control and mapping."
    ), ECommerceItem(
        id = "bacb1f1e-7f99-4fe8-a1a3-c6ba2b3ed007",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "4K TV",
        itemPrice = 499.99,
        itemRating = 4.7f,
        itemDescription = "Ultra HD 4K TV with smart features and HDR."
    ), ECommerceItem(
        id = "c039e0a6-fcab-4b2c-a1fc-b626631facdb",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Home Security Camera",
        itemPrice = 59.99,
        itemRating = 4.3f,
        itemDescription = "Wi-Fi security camera with night vision and motion detection."
    ), ECommerceItem(
        id = "22f15841-49f9-48bd-b7ac-fee73236534f",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Digital Photo Frame",
        itemPrice = 79.99,
        itemRating = 4.4f,
        itemDescription = "Wi-Fi digital photo frame with auto-rotate."
    ), ECommerceItem(
        id = "03f2950f-2032-4ae5-99b6-ec343217641b",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Smart Door Lock",
        itemPrice = 179.99,
        itemRating = 4.5f,
        itemDescription = "Keyless smart door lock with app and keypad."
    ), ECommerceItem(
        id = "0b65e38c-5a3e-408a-96de-22cb5dc695bc",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Espresso Machine",
        itemPrice = 249.99,
        itemRating = 4.6f,
        itemDescription = "Automatic espresso machine with milk frother."
    ), ECommerceItem(
        id = "5bf7b0fb-6c65-4032-9a88-08e54b5a0a62",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Streaming Device",
        itemPrice = 39.99,
        itemRating = 4.5f,
        itemDescription = "4K streaming device with voice control."
    ), ECommerceItem(
        id = "b3a6ff14-85e2-49b9-b261-b847deb66b33",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Electric Scooter",
        itemPrice = 299.99,
        itemRating = 4.4f,
        itemDescription = "Foldable electric scooter with long range."
    ), ECommerceItem(
        id = "5266d9d2-dcc7-4a54-9042-01f02804aa5d",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Air Fryer",
        itemPrice = 99.99,
        itemRating = 4.7f,
        itemDescription = "Digital air fryer for healthy cooking."
    ), ECommerceItem(
        id = "4786f3a7-1154-49d2-91eb-35a88520b0d9",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Smart Light Bulbs",
        itemPrice = 49.99,
        itemRating = 4.3f,
        itemDescription = "Color-changing smart LED light bulbs."
    ), ECommerceItem(
        id = "a4dfca67-f42b-45e5-8bdd-b3ee2e45a2c4",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Electric Shaver",
        itemPrice = 59.99,
        itemRating = 4.5f,
        itemDescription = "Rechargeable electric shaver with precision blades."
    ), ECommerceItem(
        id = "4d932a43-a0f2-4efa-b948-d2e9b48bf457",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Coffee Maker",
        itemPrice = 79.99,
        itemRating = 4.6f,
        itemDescription = "Programmable coffee maker with thermal carafe."
    ), ECommerceItem(
        id = "6bea3799-a2c6-4c54-aff9-0fb9069310ca",
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Video Doorbell",
        itemPrice = 149.99,
        itemRating = 4.4f,
        itemDescription = "Smart video doorbell with two-way audio."
    )
)