# import the necessary packages
import os.path
from skimage.metrics import structural_similarity as ssim
from skimage.filters import threshold_local
import json
from flask import Flask, request, Response
import uuid
import numpy as np
import cv2
from transform import four_point_transform


def ImageToText(image):
    path_file = ('static/%s.jpg'%uuid.uuid4().hex)
    image = cv2.resize(image, (656,483))

    image = findPage(image)

    expected = cv2.imread("wheel_challenge.jpg")
    expected = findPage(expected)

    s = ssim(image, expected)
    percent = s*100
    cv2.imwrite(path_file, image)
    with open('ssim_output.txt','w') as f:
        f.write(str(s));

    
    return json.dumps(format(percent,'.2f'))


def findPage(image):
    ratio = image.shape[0] / image.shape[1]
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    gray = cv2.GaussianBlur(gray, (5, 5), 0)
    edged = cv2.Canny(gray, 75, 200)

    cnts = cv2.findContours(edged.copy(), cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)
    cnts = grab_contours(cnts)
    cnts = sorted(cnts, key = cv2.contourArea, reverse = True)[:5]
 
    for c in cnts:
    # approximate the contour
        peri = cv2.arcLength(c, True)
        approx = cv2.approxPolyDP(c, 0.02 * peri, True)

        # if our approximated contour has four points, then we
        # can assume that we have found our screen
        if len(approx) == 4:
            screenCnt = approx
            break

    # apply the four point transform to obtain a top-down
    # view of the original image
    try:
        warped = four_point_transform(gray, screenCnt.reshape(4, 2) * ratio)
        warped = cv2.cvtColor(warped, cv2.COLOR_BGR2GRAY)
    except:
        warped = gray

    # convert the warped image to grayscale, then threshold it
    # to give it that 'black and white' paper effect
    T = threshold_local(warped, 11, offset = 10, method = "gaussian")
    warped = (warped > T).astype("uint8") * 255

    return warped


def grab_contours(cnts):
    # if the length the contours tuple returned by cv2.findContours
    # is '2' then we are using either OpenCV v2.4, v4-beta, or
    # v4-official
    if len(cnts) == 2:
        cnts = cnts[0]

    # if the length of the contours tuple is '3' then we are using
    # either OpenCV v3, v4-pre, or v4-alpha
    elif len(cnts) == 3:
        cnts = cnts[1]

    # otherwise OpenCV has changed their cv2.findContours return
    # signature yet again and I have no idea WTH is going on
    else:
        raise Exception(("Contours tuple must have length 2 or 3, "
            "otherwise OpenCV changed their cv2.findContours return "
            "signature yet again. Refer to OpenCV's documentation "
            "in that case"))

    # return the actual contours array
    return cnts



app = Flask(__name__)
@app.route("/api/upload", methods=['POST'])


def upload():
    img = cv2.imdecode(np.fromstring(request.files['image'].read(),np.uint8), cv2.IMREAD_UNCHANGED)
    img_processed = ImageToText(img)
    return Response(response=img_processed, status=200, mimetype='application/json')


app.run(host="0.0.0.0", port=5000)

if __name__ == "__main__":
    app.run()
