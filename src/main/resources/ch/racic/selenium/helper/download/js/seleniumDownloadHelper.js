/*
 * Copyleft (c) 2014. This code is for learning purposes only. Do whatever you like with it but don't take it as perfect code.
 * Michel Racic (http://rac.su/+) => github.com/rac2030
 */

var seleniumDownloadHelper = {
    getBinary: function (url) {
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
        if (xhr.status != 200) return '';
        if (IE_HACK) {
            return bin2arr(xhr.responseBody);
        } else {
            return xhr.responseText;
        }
    },

    getB64Binary: function (url) {
        var content = seleniumDownloadHelper.getBinary(url);
        return base64Encode(content);
    }
};