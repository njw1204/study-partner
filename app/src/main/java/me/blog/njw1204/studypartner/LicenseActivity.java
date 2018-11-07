package me.blog.njw1204.studypartner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LicenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        String data = "1. fontawesome\n\nFont Awesome Free License\n" +
                          "-------------------------\n" +
                          "\n" +
                          "Font Awesome Free is free, open source, and GPL friendly. You can use it for\n" +
                          "commercial projects, open source projects, or really almost whatever you want.\n" +
                          "Full Font Awesome Free license: https://fontawesome.com/license/free.\n" +
                          "\n" +
                          "# Icons: CC BY 4.0 License (https://creativecommons.org/licenses/by/4.0/)\n" +
                          "In the Font Awesome Free download, the CC BY 4.0 license applies to all icons\n" +
                          "packaged as SVG and JS file types.\n" +
                          "\n" +
                          "# Fonts: SIL OFL 1.1 License (https://scripts.sil.org/OFL)\n" +
                          "In the Font Awesome Free download, the SIL OLF license applies to all icons\n" +
                          "packaged as web and desktop font files.\n" +
                          "\n" +
                          "# Code: MIT License (https://opensource.org/licenses/MIT)\n" +
                          "In the Font Awesome Free download, the MIT license applies to all non-font and\n" +
                          "non-icon files.\n" +
                          "\n" +
                          "# Attribution\n" +
                          "Attribution is required by MIT, SIL OLF, and CC BY licenses. Downloaded Font\n" +
                          "Awesome Free files already contain embedded comments with sufficient\n" +
                          "attribution, so you shouldn't need to do anything additional when using these\n" +
                          "files normally.\n" +
                          "\n" +
                          "We've kept attribution comments terse, so we ask that you do not actively work\n" +
                          "to remove them from files, especially code. They're a great way for folks to\n" +
                          "learn about Font Awesome.\n" +
                          "\n" +
                          "# Brand Icons\n" +
                          "All brand icons are trademarks of their respective owners. The use of these\n" +
                          "trademarks does not indicate endorsement of the trademark holder by Font\n" +
                          "Awesome, nor vice versa. **Please do not use brand logos for any purpose except\n" +
                          "to represent the company, product, or service to which they refer.**\n" +
                          "\n\n2. glide\n\nLicense for everything not in third_party and not otherwise marked:\n" +
                          "\n" +
                          "Copyright 2014 Google, Inc. All rights reserved.\n" +
                          "\n" +
                          "Redistribution and use in source and binary forms, with or without modification, are\n" +
                          "permitted provided that the following conditions are met:\n" +
                          "\n" +
                          "   1. Redistributions of source code must retain the above copyright notice, this list of\n" +
                          "         conditions and the following disclaimer.\n" +
                          "\n" +
                          "   2. Redistributions in binary form must reproduce the above copyright notice, this list\n" +
                          "         of conditions and the following disclaimer in the documentation and/or other materials\n" +
                          "         provided with the distribution.\n" +
                          "\n" +
                          "THIS SOFTWARE IS PROVIDED BY GOOGLE, INC. ``AS IS'' AND ANY EXPRESS OR IMPLIED\n" +
                          "WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND\n" +
                          "FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL GOOGLE, INC. OR\n" +
                          "CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR\n" +
                          "CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR\n" +
                          "SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON\n" +
                          "ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING\n" +
                          "NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF\n" +
                          "ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n" +
                          "\n" +
                          "The views and conclusions contained in the software and documentation are those of the\n" +
                          "authors and should not be interpreted as representing official policies, either expressed\n" +
                          "or implied, of Google, Inc.\n" +
                          "---------------------------------------------------------------------------------------------\n" +
                          "License for third_party/disklrucache:\n" +
                          "\n" +
                          "Copyright 2012 Jake Wharton\n" +
                          "Copyright 2011 The Android Open Source Project\n" +
                          "\n" +
                          "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                          "you may not use this file except in compliance with the License.\n" +
                          "You may obtain a copy of the License at\n" +
                          "\n" +
                          "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                          "\n" +
                          "Unless required by applicable law or agreed to in writing, software\n" +
                          "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                          "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                          "See the License for the specific language governing permissions and\n" +
                          "limitations under the License.\n" +
                          "---------------------------------------------------------------------------------------------\n" +
                          "License for third_party/gif_decoder:\n" +
                          "\n" +
                          "Copyright (c) 2013 Xcellent Creations, Inc.\n" +
                          "\n" +
                          "Permission is hereby granted, free of charge, to any person obtaining\n" +
                          "a copy of this software and associated documentation files (the\n" +
                          "\"Software\"), to deal in the Software without restriction, including\n" +
                          "without limitation the rights to use, copy, modify, merge, publish,\n" +
                          "distribute, sublicense, and/or sell copies of the Software, and to\n" +
                          "permit persons to whom the Software is furnished to do so, subject to\n" +
                          "the following conditions:\n" +
                          "\n" +
                          "The above copyright notice and this permission notice shall be\n" +
                          "included in all copies or substantial portions of the Software.\n" +
                          "\n" +
                          "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND,\n" +
                          "EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF\n" +
                          "MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND\n" +
                          "NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE\n" +
                          "LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION\n" +
                          "OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION\n" +
                          "WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.\n" +
                          "---------------------------------------------------------------------------------------------\n" +
                          "License for third_party/gif_encoder/AnimatedGifEncoder.java and\n" +
                          "third_party/gif_encoder/LZWEncoder.java:\n" +
                          "\n" +
                          "No copyright asserted on the source code of this class. May be used for any\n" +
                          "purpose, however, refer to the Unisys LZW patent for restrictions on use of\n" +
                          "the associated LZWEncoder class. Please forward any corrections to\n" +
                          "kweiner@fmsware.com.\n" +
                          "\n" +
                          "-----------------------------------------------------------------------------\n" +
                          "License for third_party/gif_encoder/NeuQuant.java\n" +
                          "\n" +
                          "Copyright (c) 1994 Anthony Dekker\n" +
                          "\n" +
                          "NEUQUANT Neural-Net quantization algorithm by Anthony Dekker, 1994. See\n" +
                          "\"Kohonen neural networks for optimal colour quantization\" in \"Network:\n" +
                          "Computation in Neural Systems\" Vol. 5 (1994) pp 351-367. for a discussion of\n" +
                          "the algorithm.\n" +
                          "\n" +
                          "Any party obtaining a copy of these files from the author, directly or\n" +
                          "indirectly, is granted, free of charge, a full and unrestricted irrevocable,\n" +
                          "world-wide, paid up, royalty-free, nonexclusive right and license to deal in\n" +
                          "this software and documentation files (the \"Software\"), including without\n" +
                          "limitation the rights to use, copy, modify, merge, publish, distribute,\n" +
                          "sublicense, and/or sell copies of the Software, and to permit persons who\n" +
                          "receive copies from any such party to do so, with the only requirement being\n" +
                          "that this copyright notice remain intact.\n" +
                          "\n\n3. Book free icon : Icons made by Freepik from CC 3.0 BY" +
                          "\n\n\n4. retrofit\n\nCopyright 2013 Square, Inc.\n" +
                          "\n" +
                          "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                          "you may not use this file except in compliance with the License.\n" +
                          "You may obtain a copy of the License at\n" +
                          "\n" +
                          "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                          "\n" +
                          "Unless required by applicable law or agreed to in writing, software\n" +
                          "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                          "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                          "See the License for the specific language governing permissions and\n" +
                          "limitations under the License." +
                          "\n\n\n5. TinyDB\n\nCopyright 2014 KC Ochibili\n" +
                          "\n" +
                          "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                          "you may not use this file except in compliance with the License.\n" +
                          "You may obtain a copy of the License at\n" +
                          "\n" +
                          "http://www.apache.org/licenses/LICENSE-2.0\n" +
                          "\n" +
                          "Unless required by applicable law or agreed to in writing, software\n" +
                          "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                          "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                          "See the License for the specific language governing permissions and\n" +
                          "limitations under the License." +
                          "\n\n\n6. wasabeef/recyclerview-animators\n\nCopyright 2018 Wasabeef\n" +
                          "\n" +
                          "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                          "you may not use this file except in compliance with the License.\n" +
                          "You may obtain a copy of the License at\n" +
                          "\n" +
                          "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                          "\n" +
                          "Unless required by applicable law or agreed to in writing, software\n" +
                          "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                          "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                          "See the License for the specific language governing permissions and\n" +
                          "limitations under the License." +
                          "\n\n\n7. butterknife\n\nCopyright 2013 Jake Wharton\n" +
                          "\n" +
                          "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                          "you may not use this file except in compliance with the License.\n" +
                          "You may obtain a copy of the License at\n" +
                          "\n" +
                          "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                          "\n" +
                          "Unless required by applicable law or agreed to in writing, software\n" +
                          "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                          "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                          "See the License for the specific language governing permissions and\n" +
                          "limitations under the License." +
                          "\n\n\n8. material-calendarview\n\nCopyright (c) 2018 Prolific Interactive\n" +
                          "\n" +
                          "Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                          "of this software and associated documentation files (the \"Software\"), to deal\n" +
                          "in the Software without restriction, including without limitation the rights\n" +
                          "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                          "copies of the Software, and to permit persons to whom the Software is\n" +
                          "furnished to do so, subject to the following conditions:\n" +
                          "\n" +
                          "The above copyright notice and this permission notice shall be included in\n" +
                          "all copies or substantial portions of the Software.\n" +
                          "\n" +
                          "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                          "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                          "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                          "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                          "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                          "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN\n" +
                          "THE SOFTWARE.";
            ;

        ((TextView)findViewById(R.id.textView_license)).setText(data);
    }
}
