package com.moveandstore.citiessearchtask.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moveandstore.citiessearchtask.data.model.City
import com.moveandstore.citiessearchtask.data.model.Coord
import com.moveandstore.citiessearchtask.theme.GrayLight
import com.moveandstore.citiessearchtask.utils.navigateToGoogleMaps


@Composable
fun Cities(viewModel: CitiesVM = hiltViewModel()) {
    val searchResults by viewModel.searchResults.collectAsState()
    var query by remember { mutableStateOf("") }
    val imePadding = WindowInsets.ime.asPaddingValues()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .background(color = GrayLight)
            .fillMaxSize()
            .padding(imePadding)
    ) {


        Text(
            text = "${searchResults.size} cities",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1.0f)
                .fillMaxWidth()
        ) {
            items(searchResults, key = { it.id }) { city ->
                CityItem(city)
            }
        }

        SearchBar(
            query = query,
            onQueryChange = { newValue ->
                query = newValue
                viewModel.onSearchQueryChanged(newValue)
            },
            onClearClick = {
                query = ""
                viewModel.onSearchQueryChanged("")

                focusManager.clearFocus()
            },
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        placeholder = { Text("Search...") },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.LightGray,
            focusedContainerColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClearClick) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear"
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Composable
private fun CityItem(city: City) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .wrapContentHeight()
            .clickable {
                context.navigateToGoogleMaps(longitude = city.coord.longitude, latitude =  city.coord.latitude)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .padding(8.dp)
                .width(70.dp)
                .height(70.dp)
                .background(
                    color = Color.LightGray,
                    shape = CircleShape
                )
        ) {
            Text(
                text = city.country,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }


        Column(modifier = Modifier.weight(1.0f)) {
            Text(
                text = city.name,
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "${city.coord.latitude}, ${city.coord.longitude}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    CityItem(City(id = 0, name = "name", country = "UK", coord = Coord(0.0, 0.0)))
}