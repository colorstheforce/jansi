/*
 * Copyright (C) 2009-2020 the original author(s).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */package org.fusesource.jansi;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EncodingTest {

    @Test
    public void testEncoding8859() throws UnsupportedEncodingException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final AtomicReference<String> newLabel = new AtomicReference<String>();
        PrintStream ansi = new PrintStream(new AnsiOutputStream(baos, new AnsiProcessor(baos) {
            @Override
            protected void processChangeWindowTitle(String label) {
                newLabel.set(label);
            }
        }, "ISO-8859-1"), true, "ISO-8859-1");

        ansi.print("\033]0;un bon café\007");
        ansi.flush();
        assertEquals("un bon café", newLabel.get());
    }

    @Test
    public void testEncodingUtf8() throws UnsupportedEncodingException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final AtomicReference<String> newLabel = new AtomicReference<String>();
        PrintStream ansi = new PrintStream(new AnsiOutputStream(baos, new AnsiProcessor(baos) {
            @Override
            protected void processChangeWindowTitle(String label) {
                newLabel.set(label);
            }
        }, "UTF-8"), true, "UTF-8");

        ansi.print("\033]0;ひらがな\007");
        ansi.flush();
        assertEquals("ひらがな", newLabel.get());
    }

}
