/*
 * Copyleft (c) 2014. This code is for learning purposes only. Do whatever you like with it but don't take it as perfect code.
 * Michel Racic (http://rac.su/+) => github.com/rac2030
 */

// code taken from http://miskun.com/javascript/internet-explorer-and-binary-files-data-access/#comment-17
// Hinted at http://stackoverflow.com/a/1926163
function bin2arr(a) {
    return arr(a).replace(/[\s\S]/g, function (t) {
        var v = t.charCodeAt(0);
        return String.fromCharCode(v & 0xff, v >> 8);
    }) + arrl(a);
}

var bin2arrVBS = [
    "Function arr(t)",
    "    arr= CStr(t)",
    "End Function",
    "Function arrl(t)",
    "    arrl=\"\"",
    "    if LenB(t) mod 2 Then arrl= Chr(AscB(RightB(t,1)))",
    "End Function"
];

// Taken code from
// http://www.actionscript.org/resources/articles/745/5/JavaScript-and-VBScript-Injection-in-ActionScript-3/Page5.html
// Note, this only work until IE10, IE11+ do not have the execScript and point to eval which does not work with vbs
function injectVB(vbArray) {
    var CRLF = String.fromCharCode(13) + String.fromCharCode(10);
    var temp = '';
    for (var i = 0; i < vbArray.length; i++) {
        var vbTemp = vbArray[i];
        temp += vbTemp + String.fromCharCode(13);
    }
    window.execScript(temp, 'vbscript');
};

injectVB(bin2arrVBS);