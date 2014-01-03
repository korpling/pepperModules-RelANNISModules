/*
 * Copyright 2014 Humboldt Univerity of Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Edge;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GRAPH_TRAVERSE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GraphTraverseHandler;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Node;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SGraphTraverseHandler;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;

public class STraverseHandlerWrapper implements GraphTraverseHandler
{

  SGraphTraverseHandler traverseHandler = null;

  @Override
  public void nodeReached(GRAPH_TRAVERSE_TYPE traversalType,
    String traversalId, Node currNode, Edge edge, Node fromNode,
    long order)
  {
    traverseHandler.nodeReached(traversalType, traversalId, (SNode) currNode, (SRelation) edge, (SNode) fromNode, order);
  }

  @Override
  public void nodeLeft(GRAPH_TRAVERSE_TYPE traversalType,
    String traversalId, Node currNode, Edge edge, Node fromNode,
    long order)
  {
    traverseHandler.nodeLeft(traversalType, traversalId, (SNode) currNode, (SRelation) edge, (SNode) fromNode, order);
  }

  @Override
  public boolean checkConstraint(GRAPH_TRAVERSE_TYPE traversalType,
    String traversalId, Edge edge, Node currNode, long order)
  {
    return (traverseHandler.checkConstraint(traversalType, traversalId, (SRelation) edge, (SNode) currNode, order));
  }

}
