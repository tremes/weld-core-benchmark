/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.weld.benchmark.charts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Generate an charts for benchmarks from csv files
 *
 * @author Kirill Gaevskii
 */
public class Main {

    private static File FILES_PATH;
    public static final String DEFAULT_PATH = System.getProperty("build.directory", System.getProperty("user.dir"));

    public static void main(String... args) {
        if (args.length < 1) {
            System.err.println("Argument graphs can not be found.");
            return;
        }

        FILES_PATH = new File(args[0]);
        String[] fileNames = null;

        if (FILES_PATH.isDirectory()) {
            fileNames = FILES_PATH.list(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(".csv");
                }
            });
        } else {
            fileNames = args[0].split(",");
            FILES_PATH = new File(DEFAULT_PATH);
        }

        if (fileNames.length < 2) {
            System.err.println("It have to be at least two files for creating graphs.");
            return;
        }

        List<File> files = new ArrayList<File>();
        Arrays.stream(fileNames).forEach(f -> files.add(new File(FILES_PATH, f)));

        Map<String, Chart> chart = new HashMap<String, Chart>();
        for (File file : files) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                for (String line = null; (line = br.readLine()) != null;) {
                    // Examples of string for parsing:
                    // "org.jboss.weld.benchmark.core.construction.SimpleConstructionBenchmark.run","thrpt",1,25,70.79778680435197,2.9911112235183994,"ops/s"
                    // "org.jboss.weld.benchmark.core.construction.SimpleConstructionBenchmark.run","thrpt","1","25","70,79778680435197","2,9911112235183994","ops/s"
                    // after parsing we will have 3 groups:
                    // (1) name of a package of a benchmark without org.jboss.weld.benchmark.core, ie in example it will be "construction"
                    // (2) name of a benchmark without Benchmark, ie in example it will be "SimpleConstruction"
                    // (3) result of a benchmark in string above it is "70.79778680435197"
                    Pattern pattern = Pattern.compile("org.jboss.weld.benchmark.core.(.+)\\.(.+)Benchmark.*[0-9]+.*,\"*([0-9]+[\\.,][0-9]+)\"*,\"*[0-9]+[\\.,][0-9]+\"*,\"ops/s\"");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        // create new chart with benchmark package name (see above) if not exists
                        if (!chart.containsKey(matcher.group(1))) {
                            chart.put(matcher.group(1), new Chart(matcher.group(1), "", "ops/s"));
                        }
                        // add value to chart
                        chart.get(matcher.group(1)).addValue(Double.parseDouble(matcher.group(3).replace(',', '.')), file.getName().replace(".csv", ""), matcher.group(2));
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("File " + FILES_PATH + File.separator + file.getName() + " was not found.");
                e.printStackTrace();
                return;
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
                return;
            }
        }

        chart.forEach((name, ch) -> ch.saveImageTo(DEFAULT_PATH));
    }
}
