package com.example.shipping_tracking

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shipping_tracking.ui.theme.*

@Composable
fun ShippingTrackingApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = "onboarding") {
            composable("onboarding") {
                OnboardingScreen(onStartClick = { navController.navigate("home") })
            }
            composable("home") {
                HomeScreen(onCardClick = { navController.navigate("details") })
            }
            composable("details") {
                DetailsScreen(onBackClick = { navController.popBackStack() })
            }
            composable("chat") {
                ChatScreen()
            }
            composable("profile") {
                // Placeholder for profile
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Profile Screen")
                }
            }
        }

        // Floating Bottom Navigation Bar
        if (currentRoute == "home" || currentRoute == "chat" || currentRoute == "profile") {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp, start = 24.dp, end = 24.dp)
            ) {
                ModernBottomNavBar(
                    currentRoute = currentRoute ?: "home",
                    onItemSelected = { route ->
                        if (currentRoute != route) {
                            navController.navigate(route) {
                                popUpTo("home") { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ModernBottomNavBar(currentRoute: String, onItemSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(35.dp))
            .background(Color.White.copy(alpha = 0.9f))
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomNavItem(
            icon = Icons.Default.Home,
            isSelected = currentRoute == "home",
            onClick = { onItemSelected("home") }
        )
        BottomNavItem(
            icon = Icons.Default.Chat,
            isSelected = currentRoute == "chat",
            onClick = { onItemSelected("chat") }
        )
        BottomNavItem(
            icon = Icons.Default.Person,
            isSelected = currentRoute == "profile",
            onClick = { onItemSelected("profile") }
        )
    }
}

@Composable
fun BottomNavItem(icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) OrangePrimary else Color.Transparent,
        label = "bgColor"
    )
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else GreyText,
        label = "iconColor"
    )
    val size by animateDpAsState(
        targetValue = if (isSelected) 50.dp else 40.dp,
        label = "size"
    )

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun OnboardingScreen(onStartClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_box),
                contentDescription = null,
                tint = OrangePrimary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "2800 Successful Delivery",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkText
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Your Ultimate",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Shipping Companion",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = OrangePrimary,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Pager indicator-like dots
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            repeat(4) { i ->
                Box(
                    modifier = Modifier
                        .size(if (i == 2) 24.dp else 8.dp, 8.dp)
                        .clip(CircleShape)
                        .background(if (i == 2) OrangePrimary else Color.LightGray)
                )
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Illustrative part (Truck and boxes)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.Center
        ) {
            // Placeholder for the 3D Truck illustration
            Image(
                painter = painterResource(id = R.drawable.ic_truck),
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Fit
            )
            
            // Small Floating Box icons to mimic the design
            Icon(
                painter = painterResource(id = R.drawable.ic_box),
                contentDescription = null,
                tint = OrangePrimary,
                modifier = Modifier.size(40.dp).offset(x = (-100).dp, y = (-60).dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_box),
                contentDescription = null,
                tint = OrangePrimary,
                modifier = Modifier.size(50.dp).offset(x = 100.dp, y = 80.dp)
            )
        }

        Spacer(modifier = Modifier.weight(0.5f))
        
        // Start Button
        Button(
            onClick = onStartClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(30.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            listOf(OrangeGradientStart, OrangeGradientEnd)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Text("Start", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.3f), CircleShape)
                            .padding(4.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun HomeScreen(onCardClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_truck),
                    contentDescription = null,
                    tint = DarkText,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Shipping to", fontSize = 12.sp, color = GreyText)
                    Text("11/2 Tebing, UPNVJ", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DarkText)
                }
            }
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Notifications, contentDescription = null, tint = DarkText)
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background), // Placeholder for profile image
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(OrangePrimary)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Search Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(Color.White)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Search, contentDescription = null, tint = GreyText)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Search", color = GreyText, modifier = Modifier.weight(1f))
            Icon(Icons.Default.DateRange, contentDescription = null, tint = DarkText)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Your Recently",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )
        Text(
            text = "Shipping",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = OrangePrimary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Shipping Cards
        ShippingCard("Transit", "Jakarta", "UPNVJ", "20 Feb 2026", "24 Feb 2026", "#ER 550-154-57N", onCardClick)
        Spacer(modifier = Modifier.height(16.dp))
        ShippingCard("Transit", "Alabama", "Alabama", "20 Feb 2026", "24 Feb 2026", "#ER 550-154-57N", onCardClick)
        
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ShippingCard(status: String, from: String, to: String, fromDate: String, toDate: String, id: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = OrangeLight),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painterResource(id = R.drawable.ic_box), contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(status, color = OrangePrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = OrangePrimary,
                    modifier = Modifier
                        .background(Color.White, CircleShape)
                        .padding(6.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Progress tracker
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(OrangePrimary))
                Box(modifier = Modifier.height(2.dp).weight(1f).background(OrangePrimary))
                Box(
                    modifier = Modifier.size(32.dp).clip(CircleShape).background(OrangePrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(painterResource(id = R.drawable.ic_truck), contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                }
                Box(modifier = Modifier.height(2.dp).weight(1f).background(Color.LightGray))
                Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(Color.LightGray))
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(from, fontSize = 12.sp, color = GreyText)
                    Text(fromDate, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DarkText)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(to, fontSize = 12.sp, color = GreyText)
                    Text(toDate, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DarkText)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(id, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DarkText)
                Icon(
                    painter = painterResource(id = R.drawable.ic_box),
                    contentDescription = null,
                    tint = OrangePrimary,
                    modifier = Modifier.size(80.dp).offset(y = 20.dp)
                )
            }
        }
    }
}

@Composable
fun DetailsScreen(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        // App Bar
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBackClick, modifier = Modifier.background(Color.White, CircleShape)) {
                Icon(Icons.Default.ArrowBack, contentDescription = null, tint = DarkText)
            }
            Text("Details", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkText)
            IconButton(onClick = {}, modifier = Modifier.background(Color.White, CircleShape)) {
                Icon(Icons.Default.Share, contentDescription = null, tint = DarkText)
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Map Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.LightGray)
        ) {
            // Drawing a simulated city grid
            Canvas(modifier = Modifier.fillMaxSize()) {
                val step = 40.dp.toPx()
                for (i in 0..size.width.toInt() step step.toInt()) {
                    drawLine(Color.White, Offset(i.toFloat(), 0f), Offset(i.toFloat(), size.height), 2f)
                }
                for (i in 0..size.height.toInt() step step.toInt()) {
                    drawLine(Color.White, Offset(0f, i.toFloat()), Offset(size.width, i.toFloat()), 2f)
                }
                
                // Route line
                val path = Path().apply {
                    moveTo(size.width * 0.2f, size.height * 0.8f)
                    lineTo(size.width * 0.4f, size.height * 0.6f)
                    lineTo(size.width * 0.7f, size.height * 0.7f)
                    lineTo(size.width * 0.9f, size.height * 0.3f)
                }
                drawPath(path, OrangePrimary, style = Stroke(width = 8f))
            }
            
            // Status overlay on map
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.8f))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color.Green)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Transit",
                        color = DarkText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Info Section
        Card(
            modifier = Modifier.fillMaxWidth().weight(1f),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                // Customer Info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp).clip(CircleShape).background(OrangePrimary)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("2410501080", fontSize = 12.sp, color = GreyText)
                        Text("Tebing Rizky", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkText)
                    }
                    Row {
                        IconButton(onClick = {}, modifier = Modifier.background(OrangeLight, CircleShape)) {
                            Icon(Icons.Default.Chat, contentDescription = null, tint = OrangePrimary)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = {}, modifier = Modifier.background(OrangeLight, CircleShape)) {
                            Icon(Icons.Default.Call, contentDescription = null, tint = OrangePrimary)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("From", fontSize = 12.sp, color = GreyText)
                        Text("Jakarta", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DarkText)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Customer", fontSize = 12.sp, color = GreyText)
                        Text("Tebing", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DarkText)
                    }
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                        Text("TO", fontSize = 12.sp, color = GreyText)
                        Text("Alabama 6792MK", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DarkText)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Date", fontSize = 12.sp, color = GreyText)
                        Text("Monday, 20 Feb", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DarkText)
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("#ER", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = DarkText)
                        Text("550-154-57N", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = DarkText)
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_box),
                        contentDescription = null,
                        tint = OrangePrimary,
                        modifier = Modifier.size(100.dp)
                    )
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                // Live Tracking Button
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(30.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(OrangeGradientStart, OrangeGradientEnd)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        ) {
                             Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text("Live Tracking", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.weight(1f))
                             Icon(
                                Icons.Default.KeyboardDoubleArrowRight,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.5f),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

data class ChatMessage(
    val name: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int,
    val profileIcon: ImageVector
)

@Composable
fun ChatScreen() {
    val chats = listOf(
        ChatMessage("Leslie Alexander", "The package has been delivered!", "10:30 AM", 2, Icons.Default.Person),
        ChatMessage("Guy Hawkins", "Where is my order #ER-550?", "Yesterday", 0, Icons.Default.Face),
        ChatMessage("Jenny Wilson", "Thanks for the quick shipping!", "Yesterday", 1, Icons.Default.AccountCircle),
        ChatMessage("Robert Fox", "I will be home at 5 PM.", "Mon", 0, Icons.Default.Face),
        ChatMessage("Esther Howard", "Can I change my address?", "Sun", 0, Icons.Default.AccountCircle),
        ChatMessage("Cameron Williamson", "Is it possible to track live?", "Sat", 5, Icons.Default.Person),
        ChatMessage("Brooklyn Simmons", "Please leave it at the door.", "Fri", 0, Icons.Default.Face)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(top = 32.dp)
    ) {
        Text(
            text = "Messages",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .background(Color.White)
                .padding(top = 16.dp)
        ) {
            items(chats) { chat ->
                ChatItemRow(chat)
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    thickness = 0.5.dp,
                    color = Color.LightGray.copy(alpha = 0.3f)
                )
            }
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun ChatItemRow(chat: ChatMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(OrangeLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = chat.profileIcon,
                contentDescription = null,
                tint = OrangePrimary,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chat.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
                Text(
                    text = chat.time,
                    fontSize = 12.sp,
                    color = GreyText
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chat.lastMessage,
                    fontSize = 14.sp,
                    color = GreyText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                
                if (chat.unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(OrangePrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = chat.unreadCount.toString(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    Shipping_trackingTheme {
        OnboardingScreen(onStartClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Shipping_trackingTheme {
        HomeScreen(onCardClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsPreview() {
    Shipping_trackingTheme {
        DetailsScreen(onBackClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ChatPreview() {
    Shipping_trackingTheme {
        ChatScreen()
    }
}
