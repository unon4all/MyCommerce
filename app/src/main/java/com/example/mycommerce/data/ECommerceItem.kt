package com.example.mycommerce.data

import java.util.UUID


data class ECommerceItem(
    val id: String = UUID.randomUUID().toString(),
    val imageUrl: String,
    val itemName: String,
    val itemPrice: Double,
    val itemRating: Float,
    val itemDescription: String,
)


val eCommerceItemsList = listOf(
    ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Wireless Mouse",
        itemPrice = 29.99,
        itemRating = 4.5f,
        itemDescription = "Ergonomic wireless mouse with fast scrolling."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Bluetooth Speaker",
        itemPrice = 49.99,
        itemRating = 4.7f,
        itemDescription = "Portable Bluetooth speaker with powerful sound."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Smart Watch",
        itemPrice = 199.99,
        itemRating = 4.3f,
        itemDescription = "Stylish smart watch with health tracking features."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Laptop Stand",
        itemPrice = 39.99,
        itemRating = 4.6f,
        itemDescription = "Adjustable laptop stand for better ergonomics."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "USB-C Hub",
        itemPrice = 24.99,
        itemRating = 4.2f,
        itemDescription = "Multi-port USB-C hub with HDMI and Ethernet."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Noise Cancelling Headphones",
        itemPrice = 149.99,
        itemRating = 4.8f,
        itemDescription = "Over-ear headphones with active noise cancellation."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Smart Home Hub",
        itemPrice = 89.99,
        itemRating = 4.4f,
        itemDescription = "Control your smart home devices with ease."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Electric Kettle",
        itemPrice = 29.99,
        itemRating = 4.5f,
        itemDescription = "Fast boiling electric kettle with auto shut-off."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Fitness Tracker",
        itemPrice = 59.99,
        itemRating = 4.3f,
        itemDescription = "Track your daily activity and workouts."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Portable Charger",
        itemPrice = 19.99,
        itemRating = 4.6f,
        itemDescription = "Compact portable charger with high capacity."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "LED Desk Lamp",
        itemPrice = 34.99,
        itemRating = 4.4f,
        itemDescription = "Adjustable LED desk lamp with touch control."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Wireless Earbuds",
        itemPrice = 79.99,
        itemRating = 4.5f,
        itemDescription = "True wireless earbuds with long battery life."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Gaming Keyboard",
        itemPrice = 99.99,
        itemRating = 4.7f,
        itemDescription = "Mechanical gaming keyboard with RGB lighting."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Smart Thermostat",
        itemPrice = 129.99,
        itemRating = 4.4f,
        itemDescription = "Energy-saving smart thermostat with app control."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Instant Pot",
        itemPrice = 89.99,
        itemRating = 4.8f,
        itemDescription = "Multi-functional pressure cooker and slow cooker."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Electric Toothbrush",
        itemPrice = 49.99,
        itemRating = 4.5f,
        itemDescription = "Rechargeable electric toothbrush with timer."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Air Purifier",
        itemPrice = 129.99,
        itemRating = 4.6f,
        itemDescription = "HEPA air purifier for clean and fresh air."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Robot Vacuum",
        itemPrice = 299.99,
        itemRating = 4.4f,
        itemDescription = "Smart robot vacuum with app control and mapping."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "4K TV",
        itemPrice = 499.99,
        itemRating = 4.7f,
        itemDescription = "Ultra HD 4K TV with smart features and HDR."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Home Security Camera",
        itemPrice = 59.99,
        itemRating = 4.3f,
        itemDescription = "Wi-Fi security camera with night vision and motion detection."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Digital Photo Frame",
        itemPrice = 79.99,
        itemRating = 4.4f,
        itemDescription = "Wi-Fi digital photo frame with auto-rotate."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Smart Door Lock",
        itemPrice = 179.99,
        itemRating = 4.5f,
        itemDescription = "Keyless smart door lock with app and keypad."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Espresso Machine",
        itemPrice = 249.99,
        itemRating = 4.6f,
        itemDescription = "Automatic espresso machine with milk frother."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Streaming Device",
        itemPrice = 39.99,
        itemRating = 4.5f,
        itemDescription = "4K streaming device with voice control."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Electric Scooter",
        itemPrice = 299.99,
        itemRating = 4.4f,
        itemDescription = "Foldable electric scooter with long range."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Air Fryer",
        itemPrice = 99.99,
        itemRating = 4.7f,
        itemDescription = "Digital air fryer for healthy cooking."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Smart Light Bulbs",
        itemPrice = 49.99,
        itemRating = 4.3f,
        itemDescription = "Color-changing smart LED light bulbs."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Electric Shaver",
        itemPrice = 59.99,
        itemRating = 4.5f,
        itemDescription = "Rechargeable electric shaver with precision blades."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Coffee Maker",
        itemPrice = 79.99,
        itemRating = 4.6f,
        itemDescription = "Programmable coffee maker with thermal carafe."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Video Doorbell",
        itemPrice = 149.99,
        itemRating = 4.4f,
        itemDescription = "Smart video doorbell with two-way audio."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Cordless Vacuum",
        itemPrice = 199.99,
        itemRating = 4.5f,
        itemDescription = "Lightweight cordless vacuum with powerful suction."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Smart Scale",
        itemPrice = 49.99,
        itemRating = 4.4f,
        itemDescription = "Smart body scale with app and multiple metrics."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Digital Thermometer",
        itemPrice = 19.99,
        itemRating = 4.6f,
        itemDescription = "Instant-read digital thermometer for accurate measurements."
    ), ECommerceItem(
        imageUrl = "https://picsum.photos/200/300",
        itemName = "Home Theater System",
        itemPrice = 399.99,
        itemRating = 4.5f,
        itemDescription = "Surround sound home theater system with wireless speakers."
    )
)

