import java.util.*;

import org.junit.platform.reporting.shadow.org.opentest4j.reporting.schema.QualifiedName;

public class Traversals {

  /**
   * Returns the sum of the values of all leaf nodes in the given tree of integers.
   * A leaf node is defined as a node with no children.
   * If node is null, this method returns 0.
   *
   * @param node the node of the tree
   * @return the sum of leaf node values, or 0 if the tree is null
   */
  public static int sumLeafNodes(TreeNode<Integer> node) {
    if(node == null){
      return 0;
    }

    // int sum = 0;

    if(node.right == null && node.left == null){
      return node.value;
    }
    
    //return the sum of the leafe directly like this to carry over the sum into the recursive call
    return sumLeafNodes(node.left) + sumLeafNodes(node.right);
  }

  /**
   * Counts the number of internal nodes (non-leaf nodes) in the given tree of integers.
   * An internal node has at least one child.
   * If node is null, this method returns 0.
   *
   * @param node the node of the tree
   * @return the count of internal nodes, or 0 if the tree is null
   */
  public static int countInternalNodes(TreeNode<Integer> node) {
    
    if(node == null || (node.left == null && node.right == null)){
      return 0;         //root
    }
 
    
    return 1 + countInternalNodes(node.left) + countInternalNodes(node.right);
  }

  /**
   * Creates a string by concatenating the string representation of each node's value
   * in a post-order traversal of the tree. For example, if the post-order visitation
   * encounters values "a", "b", and "c" in that order, the result is "abc".
   * If node is null, returns an empty string.
   *
   * @param node the node of the tree
   * @param <T>  the type of values stored in the tree
   * @return a post-order traversal string, or an empty string if the tree is null
   */
  public static <T> String buildPostOrderString(TreeNode<T> node) {


    if(node == null){
      return "";
    }

    StringBuilder str = new StringBuilder();

    str.append(buildPostOrderString(node.left));
    str.append(buildPostOrderString(node.right));
    
    str.append(node.value);
    
    return str.toString();

  }

  /**
   * Collects the values of all nodes in the tree level by level, from top to bottom.
   * If node is null, returns an empty list.
   *
   * @param node the node of the tree
   * @param <T>  the type of values stored in the tree
   * @return a list of node values in a top-to-bottom order, or an empty list if the tree is null
   */
  public static <T> List<T> collectLevelOrderValues(TreeNode<T> node) {

    List<T> list = new ArrayList<>();

   if(node == null){
    return list;
   }

// do put it in order a queue is needed - duh 
  Queue<TreeNode<T>> queue = new LinkedList<>();
  queue.add(node);

  while(!queue.isEmpty()){

    TreeNode<T> current = queue.poll();
    list.add(current.value);


    if(current.left != null) {
      queue.add(current.left);
    }

    if(current.right != null) {
      queue.add(current.right);
    }
  }
    return list;

  }

  /**
   * Counts the distinct values in the given tree.
   * If node is null, returns 0.
   *
   * @param node the node of the tree
   * @return the number of unique values in the tree, or 0 if the tree is null
   */
  public static int countDistinctValues(TreeNode<Integer> node) {

    if(node == null){
      return 0;
    }
    Set<Integer> uniqueNumber = new HashSet<>();
    Stack<TreeNode<Integer>> stack = new Stack<>();
    stack.push(node); // need to initialize the stack first?!

    while(!stack.isEmpty()){
      TreeNode<Integer> current = stack.pop();
      uniqueNumber.add(current.value);
      if(current.right != null){
        stack.push(current.right);
      }
      if(current.left != null){
        stack.push(current.left);
      }
    }
    // if(!uniqueNumber.contains(node.value)){
    //   uniqueNumber.add(node.value);
    // }

    // countDistinctValues(node.left);
    // countDistinctValues(node.right);

    return uniqueNumber.size();
  }

  /**
   * Determines whether there is at least one root-to-leaf path in the tree
   * where each successive node's value is strictly greater than the previous node's value.
   * If node is null, returns false.
   *
   * @param node the node of the tree
   * @return true if there exists a strictly increasing root-to-leaf path, false otherwise
   */
  public static boolean hasStrictlyIncreasingPath(TreeNode<Integer> node) {
    if(node == null) {
      return false;
    }

      Stack<TreeNode<Integer>> nodeStack = new Stack<>();
      nodeStack.push(node);
      Stack<Integer> valueStack = new Stack<>();
      valueStack.push(node.value);

      while (!nodeStack.isEmpty()) {
        TreeNode<Integer> currentNode = nodeStack.pop();
        int lastValue = valueStack.pop();  // The value to compare against.
    
        // If it's a leaf and we got here with a valid path, return true.
        if (currentNode.left == null && currentNode.right == null) {
            return true;
        }
    
        // Push the right child if its value is greater.
        if (currentNode.right != null && currentNode.right.value > lastValue) {
            nodeStack.push(currentNode.right);
            valueStack.push(currentNode.right.value);
        }
    
        // Push the left child if its value is greater.
        if (currentNode.left != null && currentNode.left.value > lastValue) {
            nodeStack.push(currentNode.left);
            valueStack.push(currentNode.left.value);
        }
    }

    return false;
  }

  // OPTIONAL CHALLENGE
  /**
   * Checks if two trees have the same shape. Two trees have the same shape
   * if they have exactly the same arrangement of nodes, irrespective of the node values.
   * If both trees are null, returns true. If one is null and the other is not, returns false.
   *
   * @param nodeA the node of the first tree
   * @param nodeB the node of the second tree
   * @param <T>   the type of values stored in the trees
   * @return true if the trees have the same shape, false otherwise
   */
  public static <T> boolean haveSameShape(TreeNode<T> nodeA, TreeNode<T> nodeB) {
    return false;
  }


  // OPTIONAL CHALLENGE
  // Very challenging!
  // Hints:
  // List.copyOf may be helpful
  // Consider adding a helper method
  // Consider keeping the current path in a separate variable
  // Consider removing the current node from the current path after the node's subtrees have been traversed.
  /**
   * Finds all paths from the root to every leaf in the given tree.
   * Each path is represented as a list of node values from root to leaf.
   * The paths should be added pre-order.
   * If node is null, returns an empty list.
   * 
   * Example:
   *
   *         1
   *        / \
   *       2   3
   *      / \    \
   *     4   5    6
   * 
   * Expected output:
   *   [[1, 2, 4], [1, 2, 5], [1, 3, 6]]
   * 
   * @param node the root node of the tree
   * @return a list of lists, where each inner list represents a root-to-leaf path in pre-order
   */
  public static <T> List<List<T>> findAllRootToLeafPaths(TreeNode<T> node) {
    return null;
  }
}
