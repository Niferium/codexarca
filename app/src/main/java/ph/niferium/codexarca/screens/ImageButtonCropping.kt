package ph.niferium.codexarca.screens


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File

@Composable
fun ImagePickerButtonCropping(
    onImageSelected: (Bitmap, ByteArray, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showCropScreen by remember { mutableStateOf(false) }
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val cameraGranted = permissions[Manifest.permission.CAMERA] == true
        val storageGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true

        if (cameraGranted || storageGranted) {
            showDialog = true
        } else {
            Toast.makeText(context, "Permissions required", Toast.LENGTH_SHORT).show()
        }
    }

    // Create temp file for camera capture
    val tempImageFile = remember {
        File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
    }

    val cameraUri = remember {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            tempImageFile
        )
    }

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imageUri = cameraUri
            // Load bitmap for cropping instead of direct processing
            loadBitmapForCropping(context, cameraUri) { bitmap ->
                capturedBitmap = bitmap
                showCropScreen = true
            }
        }
    }

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            imageUri = it
            processSelectedImage(context, it, onImageSelected)
        }
    }

    Column(modifier = modifier) {
        // Main button
        Button(
            onClick = {
                val permissions = mutableListOf<String>()

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(Manifest.permission.CAMERA)
                }

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }

                if (permissions.isNotEmpty()) {
                    permissionLauncher.launch(permissions.toTypedArray())
                } else {
                    showDialog = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Select Image")
        }

        // Show selected image
        selectedImageBitmap?.let { bitmap ->
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }

    // Selection dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Select Image") },
            text = { Text("Choose how to select your image") },
            confirmButton = {
                Row {
                    TextButton(
                        onClick = {
                            showDialog = false
                            cameraLauncher.launch(cameraUri)
                        }
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Camera")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(
                        onClick = {
                            showDialog = false
                            galleryLauncher.launch("image/*")
                        }
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Gallery")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Crop screen overlay
    if (showCropScreen && capturedBitmap != null) {
        CropScreen(
            bitmap = capturedBitmap!!,
            onCropComplete = { croppedBitmap ->
                selectedImageBitmap = croppedBitmap
                val byteArray = bitmapToByteArray(croppedBitmap)
                val fileName = "camera_image_${System.currentTimeMillis()}.jpg"
                onImageSelected(croppedBitmap, byteArray, fileName)
                showCropScreen = false
                capturedBitmap = null
            },
            onCropCancel = {
                showCropScreen = false
                capturedBitmap = null
            }
        )
    }
}

private fun loadBitmapForCropping(
    context: Context,
    uri: Uri,
    onBitmapLoaded: (Bitmap) -> Unit
) {
    try {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
        onBitmapLoaded(bitmap)
    } catch (e: Exception) {
        Toast.makeText(context, "Error loading image: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun CropScreen(
    bitmap: Bitmap,
    onCropComplete: (Bitmap) -> Unit,
    onCropCancel: () -> Unit
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    // Crop overlay dimensions (square crop)
    val cropSize = minOf(screenWidth, screenHeight) * 0.8f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.9f))
    ) {
        // Main image with transformations
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
        ) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Image to crop",
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offsetX,
                        translationY = offsetY
                    )
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale = (scale * zoom).coerceIn(0.5f, 5f)
                            offsetX += pan.x
                            offsetY += pan.y
                        }
                    },
                contentScale = ContentScale.Fit
            )
        }

        // Crop overlay
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val cropSizePx = cropSize.toPx()
            val halfCropSize = cropSizePx / 2

            // Draw dark overlay with transparent crop area
            drawRect(
                color = Color.Black.copy(alpha = 0.5f),
                topLeft = Offset(0f, 0f),
                size = Size(size.width, centerY - halfCropSize)
            )
            drawRect(
                color = Color.Black.copy(alpha = 0.5f),
                topLeft = Offset(0f, centerY + halfCropSize),
                size = Size(size.width, size.height - (centerY + halfCropSize))
            )
            drawRect(
                color = Color.Black.copy(alpha = 0.5f),
                topLeft = Offset(0f, centerY - halfCropSize),
                size = Size(centerX - halfCropSize, cropSizePx)
            )
            drawRect(
                color = Color.Black.copy(alpha = 0.5f),
                topLeft = Offset(centerX + halfCropSize, centerY - halfCropSize),
                size = Size(size.width - (centerX + halfCropSize), cropSizePx)
            )

            // Draw crop boundary
            drawRect(
                color = Color.White,
                topLeft = Offset(centerX - halfCropSize, centerY - halfCropSize),
                size = Size(cropSizePx, cropSizePx),
                style = Stroke(width = 3.dp.toPx())
            )

            // Draw grid lines
            val gridColor = Color.White.copy(alpha = 0.5f)
            val third = cropSizePx / 3

            // Vertical lines
            drawLine(
                color = gridColor,
                start = Offset(centerX - halfCropSize + third, centerY - halfCropSize),
                end = Offset(centerX - halfCropSize + third, centerY + halfCropSize),
                strokeWidth = 1.dp.toPx()
            )
            drawLine(
                color = gridColor,
                start = Offset(centerX - halfCropSize + (third * 2), centerY - halfCropSize),
                end = Offset(centerX - halfCropSize + (third * 2), centerY + halfCropSize),
                strokeWidth = 1.dp.toPx()
            )

            // Horizontal lines
            drawLine(
                color = gridColor,
                start = Offset(centerX - halfCropSize, centerY - halfCropSize + third),
                end = Offset(centerX + halfCropSize, centerY - halfCropSize + third),
                strokeWidth = 1.dp.toPx()
            )
            drawLine(
                color = gridColor,
                start = Offset(centerX - halfCropSize, centerY - halfCropSize + (third * 2)),
                end = Offset(centerX + halfCropSize, centerY - halfCropSize + (third * 2)),
                strokeWidth = 1.dp.toPx()
            )
        }

        // Control buttons
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(32.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Cancel button
            FloatingActionButton(
                onClick = onCropCancel,
                containerColor = Color.Red,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cancel",
                    tint = Color.White
                )
            }

            // Reset button
            FloatingActionButton(
                onClick = {
                    scale = 1f
                    offsetX = 0f
                    offsetY = 0f
                },
                containerColor = Color.Gray,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reset",
                    tint = Color.White
                )
            }

            // Crop/Done button
            FloatingActionButton(
                onClick = {
                    val croppedBitmap = cropBitmap(
                        bitmap = bitmap,
                        scale = scale,
                        offsetX = offsetX,
                        offsetY = offsetY,
                        cropSize = cropSize.value.toInt(),
                        screenWidth = screenWidth.value.toInt(),
                        screenHeight = screenHeight.value.toInt()
                    )
                    onCropComplete(croppedBitmap)
                },
                containerColor = Color.Green,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Crop",
                    tint = Color.White
                )
            }
        }

        // Instructions
        Text(
            text = "Pinch to zoom • Drag to move • Tap ✓ to crop",
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

private fun cropBitmap(
    bitmap: Bitmap,
    scale: Float,
    offsetX: Float,
    offsetY: Float,
    cropSize: Int,
    screenWidth: Int,
    screenHeight: Int
): Bitmap {
    // Calculate the actual dimensions of the displayed image
    val bitmapRatio = bitmap.width.toFloat() / bitmap.height.toFloat()
    val screenRatio = screenWidth.toFloat() / screenHeight.toFloat()

    val displayWidth: Float
    val displayHeight: Float

    if (bitmapRatio > screenRatio) {
        displayHeight = screenHeight.toFloat()
        displayWidth = displayHeight * bitmapRatio
    } else {
        displayWidth = screenWidth.toFloat()
        displayHeight = displayWidth / bitmapRatio
    }

    // Calculate scale factors
    val scaleX = bitmap.width / displayWidth
    val scaleY = bitmap.height / displayHeight

    // Calculate crop area in bitmap coordinates
    val centerX = screenWidth / 2f
    val centerY = screenHeight / 2f
    val halfCropSize = cropSize / 2f

    // Calculate the crop area considering transformations
    val cropLeft = ((centerX - halfCropSize - offsetX) / scale) * scaleX
    val cropTop = ((centerY - halfCropSize - offsetY) / scale) * scaleY
    val cropRight = ((centerX + halfCropSize - offsetX) / scale) * scaleX
    val cropBottom = ((centerY + halfCropSize - offsetY) / scale) * scaleY

    // Ensure crop area is within bitmap bounds
    val left = cropLeft.coerceIn(0f, bitmap.width.toFloat()).toInt()
    val top = cropTop.coerceIn(0f, bitmap.height.toFloat()).toInt()
    val right = cropRight.coerceIn(0f, bitmap.width.toFloat()).toInt()
    val bottom = cropBottom.coerceIn(0f, bitmap.height.toFloat()).toInt()

    val width = (right - left).coerceAtLeast(1)
    val height = (bottom - top).coerceAtLeast(1)

    return Bitmap.createBitmap(bitmap, left, top, width, height)
}

private fun processSelectedImage(
    context: Context,
    uri: Uri,
    onImageSelected: (Bitmap, ByteArray, String) -> Unit
) {
    try {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }

        val compressedBitmap = compressImage(bitmap)
        val byteArray = bitmapToByteArray(compressedBitmap)
        val fileName = getFileName(context, uri) ?: "selected_image_${System.currentTimeMillis()}.jpg"

        onImageSelected(compressedBitmap, byteArray, fileName)
    } catch (e: Exception) {
        Toast.makeText(context, "Error processing image: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

private fun processCapturedImage(
    context: Context,
    uri: Uri,
    onImageSelected: (Bitmap, ByteArray, String) -> Unit
) {
    try {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }

        val compressedBitmap = compressImage(bitmap)
        val byteArray = bitmapToByteArray(compressedBitmap)
        val fileName = getFileName(context, uri) ?: "selected_image_${System.currentTimeMillis()}.jpg"

        onImageSelected(compressedBitmap, byteArray, fileName)
    } catch (e: Exception) {
        Toast.makeText(context, "Error processing image: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

private fun compressImage(bitmap: Bitmap): Bitmap {
    val maxWidth = 1024
    val maxHeight = 1024

    val width = bitmap.width
    val height = bitmap.height

    if (width <= maxWidth && height <= maxHeight) {
        return bitmap
    }

    val ratio = minOf(maxWidth.toFloat() / width, maxHeight.toFloat() / height)
    val newWidth = (width * ratio).toInt()
    val newHeight = (height * ratio).toInt()

    return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
}

private fun bitmapToByteArray(bitmap: Bitmap, quality: Int = 85): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
    return stream.toByteArray()
}

private fun getFileName(context: Context, uri: Uri): String? {
    var fileName: String? = null

    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (displayNameIndex != -1) {
                    fileName = it.getString(displayNameIndex)
                }
            }
        }
    }

    if (fileName == null) {
        fileName = uri.path?.let { path ->
            val cut = path.lastIndexOf('/')
            if (cut != -1) path.substring(cut + 1) else path
        }
    }

    return fileName
}

// Usage example
@Composable
fun ImagePickerScreenCropping() {
    var selectedImage by remember { mutableStateOf<Bitmap?>(null) }
    var imageData by remember { mutableStateOf<ByteArray?>(null) }
    var fileName by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ImagePickerButtonCropping(
            onImageSelected = { bitmap, data, name ->
                selectedImage = bitmap
                imageData = data
                fileName = name
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display selected image info
        selectedImage?.let {
            Text("Image selected: $fileName")
            Text("Size: ${imageData?.size ?: 0} bytes")
            Text("Dimensions: ${it.width} x ${it.height}")

            // Upload button example
            Button(
                onClick = {
                    imageData?.let { data ->
                        // Here you can upload the data
                        // uploadImage(data, fileName ?: "image.jpg")
                    }
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Upload Image")
            }
        }
    }
}