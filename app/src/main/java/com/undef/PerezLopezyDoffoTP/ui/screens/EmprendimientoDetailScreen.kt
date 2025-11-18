package com.undef.PerezLopezyDoffoTP.ui.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.undef.PerezLopezyDoffoTP.data.model.ContactMethod
import com.undef.PerezLopezyDoffoTP.data.model.ContactMethodType
import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento
import com.undef.PerezLopezyDoffoTP.ui.components.BackBar
import com.undef.PerezLopezyDoffoTP.ui.components.Spacer
import com.undef.PerezLopezyDoffoTP.ui.navigation.Screen
import com.undef.PerezLopezyDoffoTP.ui.viewModels.EmprendimientoDetailViewModel

@Composable
fun EmprendimientoDetailScreen(emprendimientoId: Int, navController: NavHostController) {
    val detailViewModel: EmprendimientoDetailViewModel = viewModel(
        factory = EmprendimientoDetailViewModel.provideFactory(emprendimientoId)
    )
    val emprendimiento by detailViewModel.emprendimiento.collectAsStateWithLifecycle()
    val otrosProductos by detailViewModel.otrosProductos.collectAsStateWithLifecycle()

    BackBar(
        navController = navController,
        onBack = {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Home.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            EmprendimientoDetail(
                emprendimiento = emprendimiento,
                otrosProductos = otrosProductos,
                onToggleFavorite = { detailViewModel.toggleFavorite() },
                navController = navController
            )
        }
    }
}

@Composable
fun EmprendimientoDetail(
    emprendimiento: Emprendimiento?,
    otrosProductos: List<Emprendimiento>,
    onToggleFavorite: () -> Unit,
    navController: NavHostController
) {
    if (emprendimiento == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "No encontramos este emprendimiento",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Volvé a la pantalla anterior e intentá nuevamente.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        return
    }

    val emprendedor = emprendimiento.emprendedor
    val context = LocalContext.current
    val contactMethods = remember(emprendedor) {
        val methods = emprendedor.contactMethods
        if (methods.isEmpty() && emprendedor.website.isNotBlank()) {
            listOf(
                ContactMethod(
                    type = ContactMethodType.WEBSITE,
                    value = emprendedor.website,
                    label = "Sitio web"
                )
            )
        } else {
            methods
        }
    }
    var showContactDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (showContactDialog) {
            ContactOptionsDialog(
                contactMethods = contactMethods,
                onDismiss = { showContactDialog = false },
                onContactSelected = {
                    showContactDialog = false
                    performContactAction(context, it)
                }
            )
        }
        // Imagen destacada
        Image(
            painter = rememberAsyncImagePainter(emprendimiento.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(12.dp))
                .shadow(8.dp, shape = RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nombre del emprendimiento
        Text(
            text = emprendimiento.name,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = emprendimiento.description,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Detalles del emprendimiento
        Text(text = "Categoría: ${emprendimiento.category}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Emprendedor/a",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = emprendedor.name,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = emprendedor.bio,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Ubicación: ${emprendedor.location}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Web: ${emprendedor.website}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para comprar
        Button(
            onClick = {
                if (contactMethods.isNotEmpty()) {
                    showContactDialog = true
                } else {
                    Toast.makeText(
                        context,
                        "No encontramos medios de contacto disponibles.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(contentColor = contentColorFor(MaterialTheme.colorScheme.primary))
        ) {
            Text(text = "Comunicarse con el vendedor", style = MaterialTheme.typography.bodyLarge.copy(color = Color.White))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para agregar a favoritos
        OutlinedButton(
            onClick = onToggleFavorite,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            if (!emprendimiento.isFav) {
                Text(
                    text = "Agregar a Favoritos",
                    style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary)
                )
            } else{
                Text(
                    text = "Quitar de Favoritos",
                    style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.secondary)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        ShareSection(
            onShareGeneral = { shareEmprendimiento(context, emprendimiento) },
            onShareWhatsApp = { shareOnWhatsApp(context, emprendimiento) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Otros productos de ${emprendedor.name}",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (otrosProductos.isEmpty()) {
            Text(
                text = "Este emprendedor aún no publicó más productos.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(otrosProductos) { producto ->
                    RelatedEmprendimientoCard(
                        emprendimiento = producto,
                        onClick = {
                            navController.navigate(
                                Screen.EmprendimientoDetail.route.replace(
                                    "{emprendimientoId}",
                                    producto.id.toString()
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactOptionsDialog(
    contactMethods: List<ContactMethod>,
    onDismiss: () -> Unit,
    onContactSelected: (ContactMethod) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "¿Cómo querés comunicarte?") },
        text = {
            if (contactMethods.isEmpty()) {
                Text(
                    text = "Este emprendedor aún no publicó datos de contacto.",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    contactMethods.forEach { method ->
                        ContactMethodRow(
                            method = method,
                            onClick = { onContactSelected(method) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cerrar")
            }
        }
    )
}

@Composable
private fun ContactMethodRow(
    method: ContactMethod,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = contactMethodIcon(method.type),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Column {
            Text(
                text = method.label.orEmpty().ifBlank { method.displayValue() },
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = method.displayValue(includePrefix = true),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

private fun contactMethodIcon(type: ContactMethodType) = when (type) {
    ContactMethodType.PHONE -> Icons.Filled.Phone
    ContactMethodType.WHATSAPP -> Icons.Filled.Chat
    ContactMethodType.INSTAGRAM -> Icons.Filled.CameraAlt
    ContactMethodType.EMAIL -> Icons.Filled.Email
    ContactMethodType.WEBSITE -> Icons.Filled.Public
    ContactMethodType.FACEBOOK -> Icons.Filled.Share
    ContactMethodType.OTHER -> Icons.Filled.Share
}

@Composable
private fun ShareSection(
    onShareGeneral: () -> Unit,
    onShareWhatsApp: () -> Unit
) {
    Text(
        text = "Compartir",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Contale a tus contactos sobre este emprendimiento",
        style = MaterialTheme.typography.bodySmall,
        color = Color.Gray
    )
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onShareGeneral,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Compartir")
        }
        OutlinedButton(
            onClick = onShareWhatsApp,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "WhatsApp")
        }
    }
}

@Composable
private fun RelatedEmprendimientoCard(
    emprendimiento: Emprendimiento,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Image(
            painter = rememberAsyncImagePainter(emprendimiento.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = emprendimiento.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = emprendimiento.category,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

private fun shareEmprendimiento(context: Context, emprendimiento: Emprendimiento) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, buildShareMessage(emprendimiento))
    }
    context.startActivity(Intent.createChooser(intent, "Compartir emprendimiento"))
}

private fun shareOnWhatsApp(context: Context, emprendimiento: Emprendimiento) {
    val message = buildShareMessage(emprendimiento)
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        setPackage("com.whatsapp")
        putExtra(Intent.EXTRA_TEXT, message)
    }
    try {
        context.startActivity(intent)
    } catch (error: ActivityNotFoundException) {
        Toast.makeText(context, "WhatsApp no está instalado", Toast.LENGTH_SHORT).show()
        val fallbackIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, message)
        }
        context.startActivity(Intent.createChooser(fallbackIntent, "Compartir"))
    }
}

private fun buildShareMessage(emprendimiento: Emprendimiento): String =
    """
        Descubrí ${emprendimiento.name} de ${emprendimiento.emprendedor.name}.
        ${emprendimiento.description}
        Categoría: ${emprendimiento.category} - Contacto: ${
            emprendimiento.emprendedor.contactMethods.firstOrNull()?.displayValue()
                ?: emprendimiento.emprendedor.website
        }
    """.trimIndent()

private fun performContactAction(context: Context, method: ContactMethod) {
    val intent = when (method.type) {
        ContactMethodType.PHONE -> {
            val phone = method.value.filter { it.isDigit() || it == '+' }
            if (phone.isBlank()) {
                showMissingContactToast(context)
                return
            }
            Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        }

        ContactMethodType.WHATSAPP -> {
            val digits = method.value.filter { it.isDigit() }
            if (digits.isBlank()) {
                showMissingContactToast(context)
                return
            }
            Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/$digits"))
        }

        ContactMethodType.EMAIL ->
            Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${method.value}"))

        ContactMethodType.INSTAGRAM,
        ContactMethodType.FACEBOOK,
        ContactMethodType.WEBSITE,
        ContactMethodType.OTHER -> {
            val url = method.value.ensureUrl(
                when (method.type) {
                    ContactMethodType.INSTAGRAM -> "https://instagram.com/"
                    ContactMethodType.FACEBOOK -> "https://facebook.com/"
                    else -> "https://"
                }
            )
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        }
    }
    try {
        context.startActivity(intent)
    } catch (error: ActivityNotFoundException) {
        Toast.makeText(context, "No encontramos una app para completar la acción", Toast.LENGTH_SHORT).show()
    }
}

private fun showMissingContactToast(context: Context) {
    Toast.makeText(context, "No pudimos abrir este canal de contacto", Toast.LENGTH_SHORT).show()
}

private fun ContactMethod.displayValue(includePrefix: Boolean = false): String {
    val raw = value.trim()
    return when (type) {
        ContactMethodType.INSTAGRAM -> {
            val username = raw
                .removePrefix("https://instagram.com/")
                .removePrefix("http://instagram.com/")
                .removePrefix("@")
            if (includePrefix) "@$username" else username
        }

        ContactMethodType.WHATSAPP -> raw
        else -> raw
    }
}

private fun String.ensureUrl(defaultPrefix: String): String {
    val trimmed = trim()
    if (trimmed.startsWith("http://") || trimmed.startsWith("https://")) return trimmed
    val sanitized = if (defaultPrefix.contains("instagram", true)) {
        trimmed.removePrefix("@")
    } else {
        trimmed
    }
    val needsSlash = defaultPrefix.endsWith("/")
    return if (needsSlash) "$defaultPrefix$sanitized" else defaultPrefix + sanitized
}
