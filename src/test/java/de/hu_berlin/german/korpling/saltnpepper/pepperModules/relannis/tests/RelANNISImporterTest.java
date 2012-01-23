/**
 * Copyright 2009 Humboldt University of Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.tests;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperConvertException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfaceFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testSuite.moduleTests.PepperImporterTest;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.RelANNISImporter;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusDocumentRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SMetaAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltSample.SaltSample;

public class RelANNISImporterTest extends PepperImporterTest
{	
	URI resourceURI= URI.createFileURI(new File(".").getAbsolutePath());
	URI temproraryURI= URI.createFileURI("_TMP/de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.exmaralda");
	URI specialParamsURI= URI.createFileURI("src/test/resources/EXMARaLDAImporter/specialParams/specialParams1.prop");
	
	
	protected void setUp() throws Exception 
	{
		super.setFixture(new RelANNISImporter());
		super.getFixture().setSaltProject(SaltCommonFactory.eINSTANCE.createSaltProject());
		super.setResourcesURI(resourceURI);
		super.setTemprorariesURI(temproraryURI);
		
		//setting temproraries and resources
		this.getFixture().setTemproraries(temproraryURI);
		this.getFixture().setResources(resourceURI);
		
		//set formats to support
		FormatDefinition formatDef= PepperInterfaceFactory.eINSTANCE.createFormatDefinition();
		formatDef.setFormatName("RelANNIS");
		formatDef.setFormatVersion("3.1");
		this.supportedFormatsCheck.add(formatDef);
		
		//set specialParams
		this.getFixture().setSpecialParams(specialParamsURI);
	}
	
	public void testSetGetCorpusDefinition()
	{
		//TODO somethong to test???
		CorpusDefinition corpDef= PepperInterfaceFactory.eINSTANCE.createCorpusDefinition();
		FormatDefinition formatDef= PepperInterfaceFactory.eINSTANCE.createFormatDefinition();
		formatDef.setFormatName("RelANNIS");
		formatDef.setFormatVersion("3.1");
		corpDef.setFormatDefinition(formatDef);
	}
	
	
	public void testStart1() throws IOException
	{	
		URI corpusPath= URI.createFileURI("./src/test/resources/expected/");
		//URI specialParamsURI= URI.createFileURI("./src/test/resources/EXMARaLDAImporter/Case1/specialParams1.prop");
		//this.getFixture().setSpecialParams(specialParamsURI);
		
		//start: creating and setting corpus definition
			CorpusDefinition corpDef= PepperInterfaceFactory.eINSTANCE.createCorpusDefinition();
			FormatDefinition formatDef= PepperInterfaceFactory.eINSTANCE.createFormatDefinition();
			formatDef.setFormatName("RelANNIS");
			formatDef.setFormatVersion("3.1");
			corpDef.setFormatDefinition(formatDef);
			corpDef.setCorpusPath(corpusPath);
			this.getFixture().setCorpusDefinition(corpDef);
		//end: creating and setting corpus definition
		
		//start: create sample
			//start:create corpus structure
				SDocument sDoc= SaltFactory.eINSTANCE.createSDocument();
				SElementId id = SaltFactory.eINSTANCE.createSElementId();
				id.setId("salt:/doc1");
				//sDoc.setSId("salt:/doc1");
				sDoc.setSElementId(id);
				sDoc.setSName("doc1");
				//sDoc.getSCorpusGraph().getSMetaAnnotations().add(e)
				sDoc.setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
			//end:create corpus structure
				
			SaltSample.createPrimaryData(sDoc);
			SaltSample.createTokens(sDoc);
			SaltSample.createMorphologyAnnotations(sDoc);
			SaltSample.createInformationStructureSpan(sDoc);
			SaltSample.createInformationStructureAnnotations(sDoc);
		//end: create sample
			
			SMetaAnnotation sMetaAnno= SaltCommonFactory.eINSTANCE.createSMetaAnnotation();
			sMetaAnno.setSName("version");
			sMetaAnno.setSValue("NULL");
			sDoc.addSMetaAnnotation(sMetaAnno);
			//sDoc.getSCorpusGraph().addSRelation(corpDocRel);
			Long timeToMapSDocument= System.nanoTime();
			this.start();
			Long totalTimeToMapSDocument=  (System.nanoTime() - timeToMapSDocument);
			System.out.println("Time needed for import: "+(float)totalTimeToMapSDocument/(float)1000000000 + " seconds");
		assertTrue("Documents are not equal. Differences:"+System.lineSeparator()+
				sDoc.differences(this.getFixture().getSaltProject().getSCorpusGraphs().get(0).getSDocuments().get(0))
				,sDoc.equals(this.getFixture().getSaltProject().getSCorpusGraphs().get(0).getSDocuments().get(0)));
		
//		assertTrue("Corpora are not equal. Differences:"+System.lineSeparator()+
//				sDoc.getSCorpusGraph().getSCorpusDocumentRelations().get(0).getSSource()
//					.differences(this.getFixture().getSaltProject().getSCorpusGraphs().get(0))
//				,sDoc.getSCorpusGraph().getSCorpusDocumentRelations().get(0).getSSource()
//					.equals(this.getFixture().getSaltProject().getSCorpusGraphs().get(0)));
	}
	
	
	

	

}
