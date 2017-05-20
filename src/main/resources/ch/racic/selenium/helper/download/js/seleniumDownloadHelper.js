/*
 * Copyleft (c) 2014. This code is for learning purposes only. Do whatever you like with it but don't take it as perfect code.
 * Michel Racic (http://rac.su/+) => github.com/rac2030
 */


var seleniumDownloadHelper = {
    getBinary: function (url, method, param) {
        // Mozilla/Safari/IE7+
        if (window.XMLHttpRequest) {
            var xhr = new XMLHttpRequest();
            // IE6
        } else if (window.ActiveXObject) {
            var xhr = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhr.open(method, url, false);
        if (xhr.overrideMimeType) {
            xhr.overrideMimeType('text/plain; charset=x-user-defined');
        } else {
            xhr.setRequestHeader('Accept-Charset', 'x-user-defined');
        }
		if (method == 'POST') {
			xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
		}
        xhr.send(param);

        if (xhr.status != 200) return '';

        // Code taken from http://stackoverflow.com/q/16086162
        var disp = xhr.getResponseHeader('Content-Disposition');
        var fileName = "";
        if (disp && disp.search('filename') != -1) {
            var filenamePattern = "filename=\"(.*)\".*";
            var re = new RegExp(filenamePattern);
            if (re.test(disp)) {
                fileName = re.exec(disp)[1];
            }
        }

        if (IE_HACK) {
            return [fileName, bin2arr(xhr.responseBody)];
        } else {
            return [fileName, xhr.responseText];
        }
    },

    getB64Binary: function (url, method, param) {
        var content = seleniumDownloadHelper.getBinary(url, method, param);
        return content[0] + ":contentstarts:" + base64Encode(content[1]);
    }
};