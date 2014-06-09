/*
 * Copyleft (c) 2014. This code is for learning purposes only. Do whatever you like with it but don't take it as perfect code.
 * Michel Racic (http://rac.su/+) => github.com/rac2030
 */

/**
 * Created by rac on 08.06.14.
 */
var IE_HACK = (/msie/i.test(navigator.userAgent) && !/opera/i.test(navigator.userAgent));
if (IE_HACK) {
    function bin2arr(a) {
        return arr(a).replace(/[\s\S]/g, function (t) {
            var v = t.charCodeAt(0);
            return String.fromCharCode(v & 0xff, v >> 8);
        }) + arrl(a);
    }

    var IEBinaryToArray_ByteStr_Script =
        "<!-- IEBinaryToArray_ByteStr -->\r\n" +
            "<script type='text/vbscript'>\r\n" +
            "Function arr(t)\r\n" +
            " arr= CStr(t)\r\n" +
            "End Function\r\n" +
            "Function arrl(t)\r\n" +
            " arrl=\"\" \r\n" +
            " if LenB(t) mod 2 Then arrl= Chr(AscB(RightB(t,1)))\r\n" +
            "End Function\r\n" +
            "</script>\r\n";

    // inject VBScript
    document.write(IEBinaryToArray_ByteStr_Script);
}

var seleniumDownloadHelper = {

    getBinary: function (link) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', link, false);
        var mimeoverride = true;
        try {
            xhr.overrideMimeType('text/plain; charset=x-user-defined');
        } catch (e) {
            mimeoverride = false;
        }
        xhr.send();
        if (xhr.status != 200) return '';
        return xhr.responseText;
    },

    getBinary2: function (url) {
        // Mozilla/Safari/IE7+
        if (window.XMLHttpRequest) {
            var xhr = new XMLHttpRequest();
            // IE6
        } else if (window.ActiveXObject) {
            var xhr = new ActiveXObject("Microsoft.XMLHTTP");
        }

        xhr.open("GET", url, false);
        if (xhr.overrideMimeType) {
            xhr.overrideMimeType('text/plain; charset=x-user-defined');
        } else {
            xhr.setRequestHeader('Accept-Charset', 'x-user-defined');
        }

        xhr.send(null);
        if (IE_HACK) {
            return bin2arr(xhr.responseBody);
        } else {
            return xhr.responseText;
        }
    },

    getB64Binary: function (link) {
        var content = seleniumDownloadHelper.getBinary2(link);
        //return B64.encode(content);
        return base64Encode(content);
    }
};