Function arr(t)
    arr= CStr(t)
End Function

Function arrl(t)
    arrl=\"\"
    if LenB(t) mod 2 Then arrl= Chr(AscB(RightB(t,1)))
End Function