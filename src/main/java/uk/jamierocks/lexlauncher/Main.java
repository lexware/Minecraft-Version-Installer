/*
 * This file is part of LexLauncher, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014-2017, Jamie Mansfield <https://github.com/jamierocks>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package uk.jamierocks.lexlauncher;

import com.google.inject.Guice;
import com.google.inject.Injector;
import uk.jamierocks.lexlauncher.config.ConfigManager;
import uk.jamierocks.lexlauncher.guice.LexLauncherModule;
import uk.jamierocks.lexlauncher.util.OperatingSystem;

import java.nio.file.Paths;

/**
 * The entry-point for LexLauncher.
 */
public final class Main {

    public static void main(String[] args) {
        // Set the name of the Thread
        Thread.currentThread().setName("LexLauncher Main Thread");

        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LexLauncher.log.info("LexLauncher is shutting down...");
        }));

        // Let's begin
        final ConfigManager configManager = new ConfigManager(Paths.get(OperatingSystem.getOs().getAppDataFolder(), "config.json"));
        final Injector injector = Guice.createInjector(new LexLauncherModule(configManager));
        injector.getInstance(LexLauncher.class);
    }

}
