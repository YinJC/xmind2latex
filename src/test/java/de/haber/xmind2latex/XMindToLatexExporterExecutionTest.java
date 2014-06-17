/*
 * Copyright 2014 Arne Haber
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package de.haber.xmind2latex;

import static de.haber.xmind2latex.XMindToLatexExporter.TEMPLATE_FOLDER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.io.Files;

/**
 * Execution tests for the {@link XMindToLatexExporter}.
 *
 * <br>
 * <br>
 * Copyright (c) 2014 RWTH Aachen. All rights reserved.
 *
 * @author  (last commit) $Author$
 * @version $Date$<br>
 *          $Revision$
 */
public class XMindToLatexExporterExecutionTest {
    
    @BeforeClass
    public static void setUp() {
        try {
            FileUtils.deleteDirectory(new File("target/testout"));
        }
        catch (IOException e) {
            System.err.println("Unable to clean test out directory.");
        }
    }
    
    @Test
    public void testExceptionWhenConvertingWithUnconfiguredTool() {
        try {
            new XMindToLatexExporter().convert();
            fail("ParserConfigurationException expected");
        }
        catch (Exception e) {
            assertTrue(e instanceof ParserConfigurationException);
        }
    }
    
    @Test
    public void testExecute() {
        File in = new File("src/test/resources/content.xml");
        File out = new File("target/testout/result.tex");
        if (out.exists()) {
            out.delete();
        }
        String[] args = new String[] {
                "-i", in.getAbsolutePath(),
                "-o", out.getAbsolutePath()
        };
        XMindToLatexExporter exporter;
        try {
            exporter = new XMindToLatexExporter();
            exporter.configure(args);
            assertFalse(out.exists());
            exporter.convert();
            assertTrue(out.exists());
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testExecuteNotOverwrite() throws IOException {
        File in = new File("src/test/resources/content.xml");
        File out = new File("target/testout/result.tex");
        if (out.exists()) {
            out.getParentFile().mkdirs();
            out.createNewFile();
        }
        String[] args = new String[] {
                "-i", in.getAbsolutePath(),
                "-o", out.getAbsolutePath()
        };
        XMindToLatexExporter exporter;
        try {
            exporter = new XMindToLatexExporter();
            exporter.configure(args);
            assertTrue(out.exists());
            exporter.convert();
            fail("Expected fail");
        }
        catch (Exception e) {
            assertTrue(e instanceof FileAlreadyExistsException);
        }
    }
    
    @Test
    public void testExecuteOverwrite() throws IOException {
        File in = new File("src/test/resources/content.xml");
        File out = new File("target/testout/result.tex");
        File old = new File("target/testout/old.tex");
        if (old.exists()) {
            old.delete();
        }
        out.delete();
        out.getParentFile().mkdirs();
        out.createNewFile();
        Files.copy(out, old); 
        
        String[] args = new String[] {
                "-i", in.getAbsolutePath(),
                "-o", out.getAbsolutePath(),
                "-f"
        };
        XMindToLatexExporter exporter;
        try {
            exporter = new XMindToLatexExporter();
            exporter.configure(args);
            assertTrue(out.exists());
            // old and out do not differ
            assertTrue(Files.equal(out, old));
            
            exporter.convert();
            
            // old and out differ, because out has been overwritten
            assertTrue(out.exists());
            assertFalse(Files.equal(out, old));
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testExecuteWithEnvironments() {
        File in = new File("src/test/resources/jms.xmind");
        File out = new File("target/testout/result_testExecuteWithEnvironments.tex");
        String[] args = new String[] {
                "-i", in.getAbsolutePath(),
                "-o", out.getAbsolutePath(),
                "-f",
                "-e", "4", TEMPLATE_FOLDER + "env.startEnumerate", TEMPLATE_FOLDER + "env.endEnumerate",
                "-e", "5", TEMPLATE_FOLDER + "env.startItemize", TEMPLATE_FOLDER + "env.endItemize",
                "-l", "4", TEMPLATE_FOLDER + "env.item",
                "-l", "5", TEMPLATE_FOLDER + "env.item"
        };
        XMindToLatexExporter exporter;
        try {
            exporter = new XMindToLatexExporter();
            exporter.configure(args);
            exporter.convert();
            assertTrue(out.exists());
            String content = FileUtils.readFileToString(out);
            assertTrue(content.contains("\\item"));
            assertTrue(content.contains("\\begin{enumerate}"));
            assertTrue(content.contains("\\end{enumerate}"));
            assertTrue(content.contains("\\begin{itemize}"));
            assertTrue(content.contains("\\end{itemize}"));
            
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testExecuteWithLevelTemplate() {
        File in = new File("src/test/resources/content.xml");
        File out = new File("target/testout/resultWithEnv2.tex");
        String[] args = new String[] {
                "-i", in.getAbsolutePath(),
                "-o", out.getAbsolutePath(),
                "-f",
                "-l", "7", TEMPLATE_FOLDER + "env.item",
                "-l", "8", TEMPLATE_FOLDER + "env.item"
        };
        XMindToLatexExporter exporter;
        try {
            exporter = new XMindToLatexExporter();
            exporter.configure(args);
            exporter.convert();
            assertTrue(out.exists());
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testExecuteXMindFile() {
        File in = new File("src/test/resources/example.xmind");
        File tmp = new File("target/testout/tmp_testExecuteXMindFile");
        File out = new File("target/testout/result_testExecuteXMindFile.tex");
        if (out.exists()) {
            out.delete();
        }
        String[] args = new String[] {
                "-i", in.getAbsolutePath(),
                "-o", out.getAbsolutePath()
        };
        XMindToLatexExporter exporter = new XMindToLatexExporter();
        XMindToLatexExporter.TMP_DIRECTORY = tmp.getAbsolutePath();
        try {
            assertFalse(tmp.exists());
            exporter = new XMindToLatexExporter();
            exporter.configure(args);
            assertTrue(tmp.exists());
            assertFalse(out.exists());
            exporter.convert();
            assertTrue(out.exists());
            assertFalse(tmp.exists());
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testExternalTemplate() {
        File in = new File("src/test/resources/content.xml");
        File out = new File("target/testout/testExternalTemplate.tex");
        out.delete();
        String expectedTemplate = "src/test/resources/someExternalTemplate.ftl"; 
        String[] args = new String[] {
                "-i", in.getPath(),
                "-l", "2", expectedTemplate,
                "-o", out.getAbsolutePath()
        };
        XMindToLatexExporter exporter;
        try {
            exporter = new XMindToLatexExporter();
            exporter.configure(args);
            assertEquals(5, exporter.getTemplates().size());
            assertEquals(expectedTemplate, exporter.getTemplates().get(2));
            exporter.convert();
            assertTrue(out.exists());
            String content = FileUtils.readFileToString(out);
            assertTrue(content.contains("UUAARRGGGHH"));
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testFailIfExternalTemplateNotExists() {
        File in = new File("src/test/resources/content.xml");
        File out = new File("target/testout/testFailIfExternalTemplateNotExists.tex");
        out.delete();
        String expectedTemplate = "src/test/resources/someExternalTemplateMoep.ftl"; 
        String[] args = new String[] {
                "-i", in.getPath(),
                "-l", "2", expectedTemplate,
                "-o", out.getAbsolutePath()
        };
        XMindToLatexExporter exporter;
        try {
            exporter = new XMindToLatexExporter();
            exporter.configure(args);
            exporter.convert();
        }
        catch (Exception e) {
            assertTrue(e instanceof TemplateNotExistsException);
            assertEquals(expectedTemplate, ((TemplateNotExistsException) e).getTemplate());
        }
    }
    
}
