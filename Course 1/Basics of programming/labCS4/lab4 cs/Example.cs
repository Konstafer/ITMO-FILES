using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab4_cs
{
    class Example
    {
        static void A() //main
        {
            int x = 5;
            object o = x; //Boxing
            int y = (int)o; //Unboxing

            ISportscomplex<SubscriptionGym> subscriptionGym = new SportsComplex();
            subscriptionGym.visit();

            ISportscomplex<Subscription> subscriptionPool = subscriptionGym;
            subscriptionPool.visit();

            Console.ReadLine();

        }
    }

    class Subscription
    {
        static Random rnd = new Random();

        public void Duration()
        {
            int sum = rnd.Next(1, 31);
            
        }
    }
    class SubscriptionGym : Subscription
    {
    }

    interface ISportscomplex<out T> where T : Subscription
    {
        T visit();
    }

    class SportsComplex : ISportscomplex<SubscriptionGym>
    {
        public SubscriptionGym visit()
        {
            SubscriptionGym sub = new SubscriptionGym();
            sub.Duration();
            return sub;
        }
    }
}
