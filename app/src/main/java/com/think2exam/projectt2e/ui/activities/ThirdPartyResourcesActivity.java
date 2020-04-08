package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.TPRAdapter;
import com.think2exam.projectt2e.modals.TPRModel;

import java.util.ArrayList;

public class ThirdPartyResourcesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TPRAdapter adapter;
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_party_resources);

        ArrayList<TPRModel> res = new ArrayList<>();

        res.add(
                new TPRModel(
                        "CircleImageView",
                        "Copyright 2014 - 2020 Henning Dodenhof",
                        "https://github.com/hdodenhof/CircleImageView",
                        "Licensed under the Apache License, Version 2.0 (the \"License\"); you may not use this file except in compliance with the License. You may obtain a copy of the License at",
                        "http://www.apache.org/licenses/LICENSE-2.0",
                        "Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License."
                )
        );

        res.add(
                new TPRModel(
                        "Android PinView/OtpView",
                        "Copyright (c) 2018 Mukesh Solanki",
                        "https://github.com/mukeshsolanki/android-otpview-pinview",
                        "MIT License\n" +
                                "\nPermission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the \"Software\"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:",
                        "",
                        "The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.\n"+
                                "\nTHE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE."
                )
        );

        res.add(
                new TPRModel(
                        "Gson",
                        "Copyright 2008 Google Inc.",
                        "https://github.com/google/gson",
                        "Licensed under the Apache License, Version 2.0 (the \"License\"); you may not use this file except in compliance with the License. You may obtain a copy of the License at",
                        "http://www.apache.org/licenses/LICENSE-2.0",
                        "Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License."
                )
        );


        res.add(
                new TPRModel(
                        "Glide",
                        "Multiple Copyrights",
                        "https://github.com/bumptech/glide",
                        "License for everything not in third_party and not otherwise marked:\n" +
                                "\n" +
                                "Copyright 2014 Google, Inc. All rights reserved.\n" +
                                "\n" +
                                "Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:\n" +
                                "\n" +
                                "   1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.\n" +
                                "\n" +
                                "   2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.\n" +
                                "\n" +
                                "THIS SOFTWARE IS PROVIDED BY GOOGLE, INC. ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL GOOGLE, INC. OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n" +
                                "\n" +
                                "The views and conclusions contained in the software and documentation are those of the authors and should not be interpreted as representing official policies, either expressed or implied, of Google, Inc.\n" +
                                "---------------------------------------------------------------------------------------------\n" +
                                "License for third_party/disklrucache:\n" +
                                "\n" +
                                "Copyright 2012 Jake Wharton\n" +
                                "Copyright 2011 The Android Open Source Project\n" +
                                "\n" +
                                "Licensed under the Apache License, Version 2.0 (the \"License\"); you may not use this file except in compliance with the License. You may obtain a copy of the License at\n" +
                                "\n" +
                                "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                                "\n" +
                                "Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.\n" +
                                "---------------------------------------------------------------------------------------------\n" +
                                "License for third_party/gif_decoder:\n" +
                                "\n" +
                                "Copyright (c) 2013 Xcellent Creations, Inc.\n" +
                                "\n" +
                                "Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the \"Software\"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:\n" +
                                "\n" +
                                "The above copyright notice and this permission notice shall be include d in all copies or substantial portions of the Software.\n" +
                                "\n" +
                                "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.\n" +
                                "---------------------------------------------------------------------------------------------\n" +
                                "License for third_party/gif_encoder/AnimatedGifEncoder.java and third_party/gif_encoder/LZWEncoder.java:\n" +
                                "\n" +
                                "No copyright asserted on the source code of this class. May be used for any purpose, however, refer to the Unisys LZW patent for restrictions on use of the associated LZWEncoder class. Please forward any corrections to kweiner@fmsware.com.\n" +
                                "\n" +
                                "-----------------------------------------------------------------------------\n" +
                                "License for third_party/gif_encoder/NeuQuant.java\n" +
                                "\n" +
                                "Copyright (c) 1994 Anthony Dekker\n" +
                                "\n" +
                                "NEUQUANT Neural-Net quantization algorithm by Anthony Dekker, 1994. See \"Kohonen neural networks for optimal colour quantization\" in \"Network: Computation in Neural Systems\" Vol. 5 (1994) pp 351-367. for a discussion of\n" +
                                "the algorithm.\n" +
                                "\n" +
                                "Any party obtaining a copy of these files from the author, directly or indirectly, is granted, free of charge, a full and unrestricted irrevocable, world-wide, paid up, royalty-free, nonexclusive right and license to deal in this software and documentation files (the \"Software\"), including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons who receive copies from any such party to do so, with the only requirement being that this copyright notice remain intact.",
                        "",
                        ""
                )
        );

        res.add(
                new TPRModel(
                        "Android ViewPagerIndicator",
                        "Copyright 2012 Jake Wharton\n" +
                                "Copyright 2011 Patrik Åkerfeldt\n" +
                                "Copyright 2011 Francisco Figueiredo Jr.",
                        "https://github.com/JakeWharton/ViewPagerIndicator",
                        "Licensed under the Apache License, Version 2.0 (the \"License\"); you may not use this file except in compliance with the License. You may obtain a copy of the License at",
                        "http://www.apache.org/licenses/LICENSE-2.0",
                        "Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License."
                )
        );

        res.add(
                new TPRModel(
                        "Page Indicator",
                        "Copyright (c) 2018 Chahine Mouhamad",
                        "https://github.com/chahine/pageindicator",
                        "MIT License\n"+"\n"+"Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the \"Software\"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:",
                        "",
                        "The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.\n" +
                                "\n" +
                                "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE."
                )
        );

        res.add(
                new TPRModel(
                        "Android image slider",
                        "Copyright [2019] [Ali Hosseini]",
                        "https://github.com/smarteist/Android-Image-Slider",
                        "Licensed under the Apache License, Version 2.0; you may not use this file except in compliance with the License. You may obtain a copy of the License at",
                        "http://www.apache.org/licenses/LICENSE-2.0",
                        "Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License."
                )
        );

        res.add(
                new TPRModel(
                        "HTextView",
                        "Copyright (C) 2015 [Hanks](https://github.com/hanks-zyh)",
                        "https://github.com/hanks-zyh/HTextView",
                        "Licensed under the Apache License, Version 2.0 (the \"License\"); you may not use this file except in compliance with the License. You may obtain a copy of the License at",
                        "http://www.apache.org/licenses/LICENSE-2.0",
                        "Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License."
                )
        );

        res.add(
                new TPRModel(
                        "Icons by FLaticon",
                        "Multiple Copyrights",
                        "https://www.flaticon.com",
                        "Copyrights of all icons belong to their respective artists",
                        "",
                        ""
                )
        );



        recyclerView = findViewById(R.id.tpr_recycler_view);
        adapter = new TPRAdapter(res,this);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }
}
