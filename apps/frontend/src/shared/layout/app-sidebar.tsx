import type { ComponentType } from "react";
import { NavLink, useMatch } from "react-router-dom";
import {
  ArrowLeftRight,
  Bell,
  ChevronsUpDown,
  CreditCard,
  Gift,
  House,
  Landmark,
  LifeBuoy,
  LogOut,
  PiggyBank,
  Receipt,
  Search,
  Settings,
  ShieldCheck,
  TrendingUp,
  Wallet,
} from "lucide-react";

import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarHeader,
  SidebarInput,
  SidebarMenu,
  SidebarMenuBadge,
  SidebarMenuButton,
  SidebarMenuItem,
  SidebarSeparator,
} from "@/components/ui/sidebar";

type NavItem = {
  title: string;
  to: string;
  icon: ComponentType<{ className?: string }>;
  badge?: string;
};

const bankNav: NavItem[] = [
  { title: "Overview", to: "/", icon: House },
  { title: "Accounts", to: "/accounts", icon: Landmark },
  { title: "Cards", to: "/cards", icon: CreditCard, badge: "2" },
  { title: "Move Money", to: "/move-money", icon: ArrowLeftRight },
  { title: "Pay Bills", to: "/pay-bills", icon: Receipt, badge: "3" },
  { title: "Deposits", to: "/deposits", icon: Wallet },
];

const growNav: NavItem[] = [
  { title: "Savings Goals", to: "/savings-goals", icon: PiggyBank },
  { title: "Spending Insights", to: "/spending-insights", icon: TrendingUp },
  { title: "CreditWise", to: "/creditwise", icon: ShieldCheck },
  { title: "Rewards", to: "/rewards", icon: Gift, badge: "New" },
];

const footerNav: NavItem[] = [
  { title: "Settings", to: "/settings", icon: Settings },
  { title: "Help & Support", to: "/help-support", icon: LifeBuoy },
];

export function AppSidebar() {
  return (
    <Sidebar collapsible="icon">
      <SidebarHeader>
        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarMenuButton size="lg" tooltip="Capital Two">
              <div className="flex aspect-square size-8 items-center justify-center rounded-lg bg-sidebar-primary text-sidebar-primary-foreground">
                <Landmark className="size-4" />
              </div>
              <div className="grid flex-1 text-left leading-tight">
                <span className="truncate font-medium">Capital Two</span>
                <span className="truncate text-xs text-muted-foreground">
                  Personal Banking
                </span>
              </div>
              <ChevronsUpDown className="ml-auto size-4" />
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
        <div className="relative group-data-[collapsible=icon]:hidden">
          <Search className="pointer-events-none absolute top-1/2 left-2 size-4 -translate-y-1/2 text-muted-foreground" />
          <SidebarInput placeholder="Search transactions" className="pl-8" />
        </div>
      </SidebarHeader>

      <SidebarContent>
        <SidebarGroup>
          <SidebarGroupLabel>Banking</SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              {bankNav.map((item) => (
                <NavRow key={item.title} item={item} />
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>

        <SidebarGroup>
          <SidebarGroupLabel>Grow</SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              {growNav.map((item) => (
                <NavRow key={item.title} item={item} />
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>

      <SidebarFooter>
        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarMenuButton tooltip="Notifications">
              <Bell />
              <span>Notifications</span>
              <SidebarMenuBadge>5</SidebarMenuBadge>
            </SidebarMenuButton>
          </SidebarMenuItem>
          {footerNav.map((item) => (
            <NavRow key={item.title} item={item} />
          ))}
        </SidebarMenu>

        <SidebarSeparator />

        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarMenuButton size="lg" tooltip="Jordan Rivera">
              <div className="flex aspect-square size-8 items-center justify-center rounded-lg bg-sidebar-accent text-xs font-medium">
                JR
              </div>
              <div className="grid flex-1 text-left leading-tight">
                <span className="truncate font-medium">Jordan Rivera</span>
                <span className="truncate text-xs text-muted-foreground">
                  jordan@example.com
                </span>
              </div>
              <LogOut className="ml-auto size-4" />
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarFooter>
    </Sidebar>
  );
}

function NavRow({ item }: { item: NavItem }) {
  const Icon = item.icon;
  const isActive = !!useMatch({ path: item.to, end: item.to === "/" });

  return (
    <SidebarMenuItem>
      <SidebarMenuButton
        render={<NavLink to={item.to} />}
        isActive={isActive}
        tooltip={item.title}
      >
        <Icon />
        <span>{item.title}</span>
      </SidebarMenuButton>
      {item.badge ? <SidebarMenuBadge>{item.badge}</SidebarMenuBadge> : null}
    </SidebarMenuItem>
  );
}
