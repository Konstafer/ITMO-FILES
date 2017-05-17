using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab2_cs__AGAIN_
{
    class Program
    {
       public enum Operations
        {
            addObject = 1,
            showObject,
            searchObjects,
            deleteObject
        }

        static void Main(string[] args)
        {
            GateCollection gateCollection = new GateCollection();
            Operations num = 0;
          
            while (true)
            {
                Menu(ref num, gateCollection);

                switch (num)
                {
                    case Operations.addObject:
                        AddObject(gateCollection);
                        break;

                    case Operations.showObject:
                        ShowObjects(gateCollection);
                        break;


                    case Operations.searchObjects:
                        SearchObject(gateCollection);
                        break;
                   
                    case Operations.deleteObject:
                        DeleteObject(gateCollection);
                        break;

                    default:
                        Environment.Exit(0);
                        break;
                }
            }
        }

        static public void AddObject(GateCollection gateCollection)
        {
            Console.Clear();
            bool inputValue1;
            bool inputValue2;
            int typeOfGate;
            EnterOperation(out inputValue1, out inputValue2, out typeOfGate);
            switch (typeOfGate)
            {
                case 1:
                    gateCollection.AddGate(new AND(inputValue1, inputValue2));
                    break;
                case 2:
                    gateCollection.AddGate(new OR(inputValue1, inputValue2));
                    break;
                case 3:
                    gateCollection.AddGate(new NOR(inputValue1, inputValue2));
                    break;
                case 4:
                    gateCollection.AddGate(new NOT(inputValue1, inputValue2));
                    break;
            }
        }

        static public void ShowObjects(GateCollection gateCollection)
        {
            Console.Clear();
            int index = 0;
            foreach (var item in gateCollection.GetItems())
            {
                index++;
                Console.Write("[{0}]", index);
                Console.WriteLine(item.ToString());
            }
        }

        static public bool SearchObject(GateCollection gateCollection)
        {
            Console.Clear();
            char typeOfGate;
            Console.WriteLine("Enter the type of gate for search\n 1.AND - &\n 2.OR - |\n 3.NOR - ^\n 4.NOT - !");

            try
            {
                typeOfGate = char.Parse(Console.ReadLine());
                if (typeOfGate != ('&') && typeOfGate != ('|') && typeOfGate != ('^') && typeOfGate != ('!'))
                {
                    throw new FormatException();
                }
            }
            catch
            {
                Console.WriteLine("Incorrect choise");
                return false;
            }

            if (gateCollection.Search(typeOfGate).Count == 0)
            {
                Console.WriteLine("Not found operations");
                return false;
            }
            foreach (var item in gateCollection.Search(typeOfGate))
            {
                Console.WriteLine(item.ToString());
            }
            return true;
        }

        static public void DeleteObject(GateCollection gateCollection)
        {
            Console.Clear();
            ShowObjects(gateCollection);
            Console.WriteLine("Ener index operation for removing");
            try
            {
                int index = Int32.Parse(Console.ReadLine());
                gateCollection.Removal(index);
            }
            catch
            {
                Console.WriteLine("Incorrect number");
            }

        }

        static public void Menu(ref Operations num, GateCollection gateCollection )
        {

            while (true)
            {
                Console.WriteLine("\n 1.Add operation\n 2.Show items\n 3.Search item\n 4.Delete item");
                try
                {
                    num = (Operations)Int32.Parse(Console.ReadLine());

                    if ((int)num >= 2 && (int)num <= 4 )
                    {
                        if (gateCollection.items.Count == 0)
                        {
                            Console.WriteLine("\nNo operations.");
                            continue;
                        }
                    }
                    break;
                }
                catch
                {
                    Environment.Exit(0);
                }
            }
        }

        static public void EnterOperation(out bool inputValue1, out bool inputValue2, out int typeOfGate)
        {
            while (true)
            {

                Console.WriteLine("Select gate:\n 1.AND\n 2.OR\n 3.NOR\n 4.NOT");

                try
                {
                    typeOfGate = Int32.Parse(Console.ReadLine());
                    if (typeOfGate <= 0 || typeOfGate > 4)
                    {
                        Console.WriteLine("Incorect choise");
                        continue;
                    }
                    EnterValue(out inputValue1, out inputValue2, ref typeOfGate);
                    break;
                }

                catch
                {
                    Console.WriteLine(" - Incorect choise.");
                    continue;
                }
            }

        }

        static public void EnterValue(out bool inputValue1, out bool inputValue2, ref int typeOfGate)
        {
            ConsoleKeyInfo button;
            bool flag = false;
            inputValue1 = false;
            inputValue2 = false;

            Console.Clear();

            while (true)
            {
                if (typeOfGate == 4)
                {
                    Console.WriteLine("\nEnter the input values\n (Press T if True, F if False)");
                    button = Console.ReadKey();

                    if (button.Key == ConsoleKey.T)
                    {
                        inputValue1 = true;
                        Console.Clear();
                        break;
                    }

                    if (button.Key == ConsoleKey.F)
                    {
                        inputValue1 = false;
                        Console.Clear();
                        break;
                    }
                    else
                    {
                        throw new Exception();
                    }
                }

                else
                {
                    Console.WriteLine("\nEnter the input values \n (Press T if True, F if False)");
                    button = Console.ReadKey();
                    if (flag == false)
                    {
                        flag = true;

                        if (button.Key == ConsoleKey.T)
                        {
                            inputValue1 = true;
                            continue;
                        }

                        if (button.Key == ConsoleKey.F)
                        {
                            inputValue1 = false;
                            continue;
                        }
                        else
                        {
                            throw new Exception();
                        }
                    }
                    else
                    {
                        if (button.Key == ConsoleKey.T)
                        {
                            inputValue2 = true;
                            Console.Clear();
                            break;
                        }

                        if (button.Key == ConsoleKey.F)
                        {
                            inputValue2 = false;
                            Console.Clear();
                            break;
                        }
                        else
                        {
                            throw new Exception();
                        }
                    }
                }
            }
        }


    }
}