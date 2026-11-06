package com.example.n42.study

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AssignmentTurnedIn
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Title and subtitle at the top of a screen
@Composable
fun StudyHeader(title: String, subtitle: String){
    val colors = studyColors()

    Column(modifier = Modifier.fillMaxWidth()){
        Text(
            text = title,
            modifier = Modifier.padding(start = 40.dp, top = 24.dp),
            color = colors.screenTitle,
            fontSize = 36.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = subtitle,
            modifier = Modifier.padding(start = 40.dp),
            color = colors.subtitle,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(20.dp))
        //divider line
        Box(
            modifier= Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(colors.divider)
        )
    }
}

//a full screen page with a back button, used by both detail pages
@Composable
fun DetailPage(onBackClick: () -> Unit, content: @Composable () -> Unit){
    val colors = studyColors()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 25.dp)
    )
    {
        TextButton(onClick = onBackClick){
            Text("← Back", color = colors.screenTitle, fontSize = 16.sp)
        }
        content()
        Spacer(modifier=Modifier.height(32.dp))
    }
}

//Bottom navigation bar
//The five tabs in the order they appear in the figma. The number is the tab's position
const val TAB_PROFILE = 0
const val TAB_REMINDERS = 1
const val TAB_CHAT = 2
const val TAB_TIPS = 3
const val TAB_TRIVIA = 4

private val tabNames = listOf("Profile","Reminders","Chat","Tips","Trivia")

private val tabIcons: List<ImageVector> = listOf(
    Icons.Outlined.AccountCircle,
    Icons.Outlined.Notifications,
    Icons.Outlined.ChatBubbleOutline,
    Icons.Outlined.MenuBook,
    Icons.Outlined.Science
)

@Composable
fun StudyTabBar(selectedTab: Int, onTabClick: (Int) -> Unit){
    val colors = studyColors()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(100.dp)
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
    )
    {
        for(index in 0..4){
            val isSelected = index == selectedTab
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp)
                    .background(if(isSelected) colors.tabSelected else colors.tabUnselected)
                    .clickable{onTabClick(index)},
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                Icon(
                    imageVector = tabIcons[index],
                    contentDescription = null,
                    tint = colors.tabText,
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = tabNames[index],
                    color = colors.tabText,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )
            }

            if(index <4){
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(100.dp)
                        .background(colors.tabText)
                )
            }
        }
    }
}

//Reminders
fun iconFor(type: ReminderType): ImageVector{
    return when(type){
        ReminderType.WeeklySurvey -> Icons.Outlined.AssignmentTurnedIn
        ReminderType.CookingClass -> Icons.Outlined.Restaurant
        ReminderType.Appointment -> Icons.Outlined.CalendarMonth
        ReminderType.StudyEvent -> Icons.Outlined.HourglassEmpty
    }
}

//Reminders list screen
@Composable
fun RemindersScreen(onReminderClick: (Reminder) -> Unit){
    val reminders = getReminders()

    Column(modifier = Modifier.fillMaxSize()){
        StudyHeader(title = "Reminders", subtitle = "Sent by the study team")

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 25.dp, vertical = 30.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        )
        {
            items(reminders) { reminder -> ReminderRow(
                reminder = reminder,
                onClick = {onReminderClick(reminder)}
            )
            }
        }
    }
}

@Composable
fun ReminderRow(reminder: Reminder, onClick: () -> Unit){
    val colors = studyColors()
    val shape = RoundedCornerShape(15.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(shape)
            .background(colors.card)
            .border(1.dp, colors.cardBorder,shape)
            .clickable(onClick = onClick)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Box(
            modifier= Modifier
                .size(80.dp)
                .background(colors.tile, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        )
        {
            Icon(
                imageVector = iconFor(reminder.type),
                contentDescription = reminder.type.label,
                tint = colors.onTile,
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(modifier = Modifier.width(24.dp))

        Column{
            Text(
                text = reminder.title,
                color = colors.cardTitle,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = reminder.subtitle,
                color = colors.subtitle,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

//the page you see after clicking a reminder
@Composable
fun ReminderDetailScreen(reminder: Reminder, onBackClick: () -> Unit){
    val colors = studyColors()

    DetailPage(onBackClick = onBackClick){
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(colors.tile, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        )
        {
            Icon(
                imageVector = iconFor(reminder.type),
                contentDescription = null,
                tint = colors.onTile,
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(reminder.type.label, color = colors.subtitle, fontSize = 14.sp)
        Text(
            text = reminder.title,
            color = colors.screenTitle,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(24.dp))

        DetailLine(label = "When", value = reminder.whenText)
        DetailLine(label = "Where", value = reminder.where)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = reminder.details,
            color = colors.bodyText,
            fontSize = 16.sp
        )
    }
}

@Composable
private fun DetailLine(label: String, value: String){
    val colors = studyColors()

    Column(modifier = Modifier.padding(bottom = 12.dp)){
        Text(label,color = colors.subtitle, fontSize = 14.sp)
        Text(value, color = colors.bodyText, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

//Tips list screen
@Composable
fun TipsScreen(onTipClick: (Tip) -> Unit){
    val tips = getTips()
    Column(modifier = Modifier.fillMaxSize()){
        StudyHeader(title = "Weekly Tips", subtitle = "From the Research Team")

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 25.dp, vertical = 30.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        )
        {
            items(tips){
                tip -> TipCard(
                    tip = tip,
                    onClick = {onTipClick(tip)}
                )
            }
        }
    }
}


@Composable
fun TipCard(tip: Tip, onClick: () -> Unit){
    val colors = studyColors()
    val shape = RoundedCornerShape(15.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .shadow(4.dp, shape)
            .clip(shape)
            .background(colors.tipCard)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    )
    {
        Text(
            text = tip.title,
            color = colors.onTipCard,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = tip.summary,
            color = colors.onTipCard,
            fontSize = 16.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

//the page you see after tapping a tip. Recipes also show ingredients and steps
@Composable
fun TipDetailScreen(tip: Tip, onBackClick: () -> Unit){
    val colors = studyColors()

    DetailPage(onBackClick = onBackClick){
        Text(tip.type.label, color = colors.subtitle, fontSize = 14.sp)
        Text(
            text = tip.title,
            color = colors.screenTitle,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = tip.body,
            color = colors.bodyText,
            fontSize = 16.sp
        )

        //only recipes have ingredients
        if(tip.ingredients.isNotEmpty()){
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Ingredients",
                color = colors.screenTitle,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            for(ingredient in tip.ingredients){
                Text(
                    text = "•  " + ingredient,
                    color = colors.bodyText,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }

        //only recipes have steps
        if(tip.steps.isNotEmpty()){
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Steps",
                color = colors.screenTitle,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            tip.steps.forEachIndexed{
                index, step -> Text(
                    text = "${index+1}. " + step,
                    color = colors.bodyText,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}


// The screen you see after logging in. The page for the chosen tab + the bottom bar
// Tapping a reminder or tip opens its detail page
@Composable
fun StudyHome(onBackToWelcome: (() -> Unit)? = null) {
//TEMP, just for testing. Only shows "Back to Welcome" link at the top
//delete this parameter, the link below, and the argument in MainACtivity when the real flow is done
    val colors = studyColors()

    //the things the screen remembers, aka. which tab and which detail page is open
    var selectedTab by rememberSaveable { mutableStateOf(TAB_REMINDERS) }
    var openReminderId by rememberSaveable { mutableStateOf<Int?>(null) }
    var openTipId by rememberSaveable { mutableStateOf<Int?>(null) }

    //the phones back button closes a detail page first
    BackHandler(enabled = openReminderId != null || openTipId != null) {
        openReminderId = null
        openTipId = null
    }

    //look up the reminder or tip that is open (null if none)
    val openReminder = getReminders().find { it.id == openReminderId }
    val openTip = getTips().find { it.id == openTipId }

    if (openReminder != null) {
        ReminderDetailScreen(reminder = openReminder, onBackClick = { openReminderId = null })
    } else if (openTip != null) {
        TipDetailScreen(tip = openTip, onBackClick = { openTipId = null })
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
        )
        {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .statusBarsPadding()
            )
            //TEMP test link, see the comment on the parameter above
            {
                if (onBackToWelcome != null) {
                    Text(
                        text = "← Back to Welcome (test only)",
                        color = colors.subtitle,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .clickable { onBackToWelcome() }
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }

                when (selectedTab) {
                    TAB_REMINDERS -> RemindersScreen(onReminderClick = { openReminderId = it.id })
                    TAB_TIPS -> TipsScreen(onTipClick = { openTipId = it.id })
                    else -> ComingSoon() //Profile,Chat,Trivia are built by teammate
                }
            }

            StudyTabBar(
                selectedTab = selectedTab,
                onTabClick = { selectedTab = it }
            )
        }
    }
}

//Placeholder for the tabs Hunter is building
@Composable
private fun ComingSoon(){
    val colors = studyColors()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        Text(
            text = "Coming soon",
            color = colors.subtitle,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}


//Previews
//Light and dark versions of each screen
@Preview(showBackground = true, widthDp = 390, heightDp = 700)
@Composable
fun RemindersPreviewLight(){
    Box(modifier = Modifier.fillMaxSize().background(LightStudyColors.background)){
        RemindersScreen(onReminderClick = {})
    }
}

@Preview(
    showBackground = true, widthDp = 390, heightDp = 700,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)

@Composable
fun RemindersPreviewDark(){
    Box(modifier = Modifier.fillMaxSize().background(DarkStudyColors.background)){
        RemindersScreen(onReminderClick = {})
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 700)

@Composable
fun TipsPreviewLight(){
    Box(modifier = Modifier.fillMaxSize().background(LightStudyColors.background)){
        TipsScreen(onTipClick = {})
    }
}

@Preview(
    showBackground = true, widthDp = 390, heightDp = 700,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)

@Composable
fun TipsPreviewDark(){
    Box(modifier = Modifier.fillMaxSize().background(DarkStudyColors.background)){
        TipsScreen(onTipClick = {})
    }
}

