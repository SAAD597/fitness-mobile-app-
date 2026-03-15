import urllib.request
import os

exercises = {
    "img_jump_rope.png": "Jump+Rope",
    "img_high_knees.png": "High+Knees",
    "img_burpees.png": "Burpees",
    "img_running.png": "Running",
    "img_mountain_climbers.png": "Mountain+Climbers",
    "img_jumping_jacks.png": "Jumping+Jacks",
    "img_pushups.png": "Pushups",
    "img_dips.png": "Dips",
    "img_shadow_boxing.png": "Shadow+Boxing",
    "img_kettlebell_swings.png": "Kettlebell+Swings",
    "img_box_jumps.png": "Box+Jumps",
    "img_plank_jacks.png": "Plank+Jacks"
}

output_dir = r"d:\mobile app\app\src\main\res\drawable"
os.makedirs(output_dir, exist_ok=True)

headers = {
    "User-Agent": "Mozilla/5.0"
}

for filename, text in exercises.items():
    url = f"https://ui-avatars.com/api/?name={text}&background=1976D2&color=fff&size=512&font-size=0.33"
    filepath = os.path.join(output_dir, filename)

    print(f"Downloading {filename}...")

    try:
        request = urllib.request.Request(url, headers=headers)
        response = urllib.request.urlopen(request)

        with open(filepath, "wb") as f:
            f.write(response.read())

    except Exception as e:
        print(f"Error downloading {filename}: {e}")

print("Done downloading images.")