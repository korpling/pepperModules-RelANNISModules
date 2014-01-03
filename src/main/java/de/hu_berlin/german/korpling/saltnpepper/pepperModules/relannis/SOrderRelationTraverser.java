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
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import java.util.Map;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.exceptions.RelANNISException;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GRAPH_TRAVERSE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SOrderRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SGraphTraverseHandler;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;
import org.eclipse.emf.common.util.EList;

public class SOrderRelationTraverser implements SGraphTraverseHandler
{
	/**
	 * determining order for current node  
	 */
	private long orderCounter= 0;
	
	/**
	 * stores {@link SElementId} objects and corresponding {@link RANode}-objects.
	 */
	public Map<SElementId, RANode> sElementId2RANode= null;
  public String segmentName = null;
	
	/**
	 * The initial {@link RANode}  object, which must be root of segment path. In this case, it can not get a name that easy,
	 * it must get it after the first {@link SOrderRelation} object has been traversed.
	 */
	private RANode initialNode= null;
	
	@Override
	public void nodeReached(GRAPH_TRAVERSE_TYPE traversalType,
			String traversalId, SNode currNode, SRelation sRelation,
			SNode fromNode, long order) 
	{
		RANode raNode= sElementId2RANode.get(currNode.getSElementId());
		if (raNode== null)
		{
			throw new RelANNISException("Cannot create order raNode corresponding to SNode '"+currNode.getSId()+"', because internal mapping table has no entry for given SNode. ");
		}
		if (sRelation!= null)
		{
			raNode.setSegment_name(segmentName);
			if (this.initialNode!= null)
				this.initialNode.setSegment_name(segmentName);
		}
		else
    {
			this.initialNode= raNode;
    }
    
		raNode.setLeftSegment(orderCounter);
		raNode.setRightSegment(orderCounter);
		orderCounter++;
	}

	@Override
	public void nodeLeft(GRAPH_TRAVERSE_TYPE traversalType, String traversalId,
			SNode currNode, SRelation edge, SNode fromNode, long order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkConstraint(GRAPH_TRAVERSE_TYPE traversalType,
			String traversalId, SRelation edge, SNode currNode, long order) {
		{
			if (edge== null)
				return(true);
			else
			{
				if (edge instanceof SOrderRelation)
        {
          // check if the order relation belongs to the right segmentation chain
          // as given by the segmentName
          SOrderRelation orderRel = (SOrderRelation) edge;
          EList<String> types = orderRel.getSTypes();
          if(types != null)
          {
            for(String t : types)
            {
              if(t.equals(segmentName))
              {
                return(true);
              }
            }
            
          }
        }
			}
      return(false);
		}
	}

}
