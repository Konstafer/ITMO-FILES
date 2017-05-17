using System;
using System.Collections;
using AliesQueue = System.Collections.Queue;


namespace lab_cs1
{
    class Program
    {
        static void Main(string[] args)
        {
            bool flag = true;
            string s = null;
            int n = 0;   

            ArrayList(ref flag,ref s,ref n);
            Stack(ref flag, ref s,ref n);
            Queue();
            Hashtable(ref flag,ref s,ref n);
            Console.ReadLine();

        }

        static void InputQuantity(ref bool flag, ref string s,ref int n)
        {
            flag = true;

            while (flag == true)
            {
                s = Console.ReadLine();

                if (Int32.TryParse(s, out n))
                {
                    flag = false;
                }
                else
                {
                    Console.WriteLine("Enter an integer.");
                }

                if (n <= 0)
                {
                    Console.WriteLine("Enter number more than 0.");
                    flag = true;
                }

            }
        }

        static void ArrayList(ref bool flag, ref string s, ref int n)
        {

            Console.WriteLine("\t\t ARRAYLIST\n");
            ArrayList MyArrayList = new ArrayList();
            Console.Write("Enter the number of elements of ArrayList: ");
            InputQuantity(ref flag,ref s,ref n);
            Console.WriteLine("\nEnter elements of ArrayList:");

            for (var i = 0; i < n; i++)
            {
                MyArrayList.Add(Console.ReadLine());
            }

            Console.WriteLine("\nArray List: ");

            foreach (var item in MyArrayList)
            {
                Console.WriteLine(item);
            }

            Console.Write("Enter the number of the element to remove:");
            flag = true;

            while (flag == true)
            {
                s = Console.ReadLine();

                if (Int32.TryParse(s, out n) && (n-1 < MyArrayList.Count))
                {
                    flag = false;
                    MyArrayList.RemoveAt(n-1);
                }
                else
                {
                    Console.WriteLine("Enter an integer and a number must be less than the number of elements.");
                }

                if (n < 0)
                {
                    Console.WriteLine("Enter positive number.");
                    flag = true;
                }

            }

            Console.WriteLine("\nArray List after changes: ");

            foreach (var item in MyArrayList)
            {
                Console.WriteLine(item);
            }
        }

        static void Stack(ref bool flag, ref string s, ref int n)
        {
            
            Console.WriteLine("\n\t\t STACK\n");
            Stack MyStack = new Stack();
            Console.Write("Enter the number of elements of Stack: ");
            InputQuantity(ref flag,ref s,ref n);
            Console.WriteLine("\nEnter elements of Stack:");

            for (var i = 0; i < n; i++)
            {
                MyStack.Push(Console.ReadLine());
            }

            Console.WriteLine("Peek of Stack = {0}\n", MyStack.Peek());
            Console.WriteLine("\nCount of Stack = {0}\n", MyStack.Count);
            Console.WriteLine("Stack:");

            while (MyStack.Count != 0)
            {
                Console.WriteLine(MyStack.Pop());

            }

            Console.WriteLine("\nCount of Stack after extraction = {0}\n", MyStack.Count);
        }

        static void Queue()
        {
            Console.WriteLine("\n\t\t QUEUE\n");
            AliesQueue MyQueue = new AliesQueue();
            Random rndm = new Random();

            for (var i = 0; i <= 9; i++)
            {
                MyQueue.Enqueue(rndm.NextDouble());
            }

            MyQueue.Enqueue("hello!");
            Console.WriteLine("Count of Queue = {0}", MyQueue.Count);
            Console.WriteLine("Peek of Queue = {0}", MyQueue.Peek());

            while (MyQueue.Count != 0)
            {
                Console.WriteLine("{0:0.00}", MyQueue.Dequeue());
            }

        }

        static void Hashtable(ref bool flag, ref string s, ref int n)
        {
            n = 0;
            Console.WriteLine("\n\t\t HASHTABLE\n");
            Hashtable MyHashtable = new Hashtable();
            Console.WriteLine("Enter number of people in table");
            InputQuantity(ref flag,ref s,ref n);
            int age = 0;
            string temp = null;

            for (var i = 0; i < n; i++)
            {
                Console.WriteLine("Enter Age of person {0}:", i + 1);
                flag = true;

                while (flag == true)
                {
                    s = Console.ReadLine();
                    if (Int32.TryParse(s, out age))
                    {
                        flag = false;
                    }
                    else
                    {
                        Console.WriteLine("Enter an integer.");
                    }
                    if (n < 0)
                    {
                        Console.WriteLine("Enter positive number.");
                        flag = true;
                    }

                }

                Console.WriteLine("Enter Name of person {0}:", i + 1);
                string name = Console.ReadLine();
                if (temp == name)
                {
                    Console.WriteLine("This name is in Hashtable.");
                    i = i - 1;
                    continue;
                }
                MyHashtable.Add(age, name);
                temp = name;
            }

            foreach (int key in MyHashtable.Keys)
            {
                string name = (string)MyHashtable[key];
                //Console.WriteLine("\n{0} is {1} years old", name, key);
                //Console.WriteLine("Hash Code of {0} = {1}", key, MyHashtable[key].GetHashCode());
            }

        }

    }
}
