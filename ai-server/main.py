from fastapi import FastAPI, UploadFile, File
import whisper
import os

app = FastAPI()

# whisper 로드하기
print("Loading Whisper model...")
model = whisper.load_model("base")

# 데이터 임시저장
@app.post("/transcribe")
async def transcribe_audio(file: UploadFile = File(...)):
    # 1. 파일 임시 저장
    temp_path = f"temp_{file.filename}"
    with open(temp_path, "wb") as buffer:
        buffer.write(await file.read())
    
    try:
        # 2. Whisper 분석
        print(f"Processing: {file.filename}")
        result = model.transcribe(temp_path, fp16=False)
        return {"text": result["text"]}
    finally:
        # 3. 임시 파일 삭제
        if os.path.exists(temp_path):
            os.remove(temp_path)

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)

# @app.get("/")
# def read_root():
#     return {"message": "AI Server is running!"}