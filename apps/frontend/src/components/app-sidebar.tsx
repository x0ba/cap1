import * as React from "react";
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
  icon: React.ComponentType<{ className?: string }>;
  badge?: string;
};

const bankNav: NavItem[] = [
  { title: "Overview", icon: House },
  { title: "Accounts", icon: Landmark },
  { title: "Cards", icon: CreditCard, badge: "2" },
  { title: "Move Money", icon: ArrowLeftRight },
  { title: "Pay Bills", icon: Receipt, badge: "3" },
  { title: "Deposits", icon: Wallet },
];

const growNav: NavItem[] = [
  { title: "Savings Goals", icon: PiggyBank },
  { title: "Spending Insights", icon: TrendingUp },
  { title: "CreditWise", icon: ShieldCheck },
  { title: "Rewards", icon: Gift, badge: "New" },
];

export function AppSidebar() {
  const [active, setActive] = React.useState("Overview");

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
                <NavRow
                  key={item.title}
                  item={item}
                  active={active === item.title}
                  onSelect={() => setActive(item.title)}
                />
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>

        <SidebarGroup>
          <SidebarGroupLabel>Grow</SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              {growNav.map((item) => (
                <NavRow
                  key={item.title}
                  item={item}
                  active={active === item.title}
                  onSelect={() => setActive(item.title)}
                />
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
          <SidebarMenuItem>
            <SidebarMenuButton tooltip="Settings">
              <Settings />
              <span>Settings</span>
            </SidebarMenuButton>
          </SidebarMenuItem>
          <SidebarMenuItem>
            <SidebarMenuButton tooltip="Help & Support">
              <LifeBuoy />
              <span>Help &amp; Support</span>
            </SidebarMenuButton>
          </SidebarMenuItem>
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

function NavRow({
  item,
  active,
  onSelect,
}: {
  item: NavItem;
  active: boolean;
  onSelect: () => void;
}) {
  const Icon = item.icon;
  return (
    <SidebarMenuItem>
      <SidebarMenuButton
        isActive={active}
        onClick={onSelect}
        tooltip={item.title}
      >
        <Icon />
        <span>{item.title}</span>
      </SidebarMenuButton>
      {item.badge ? <SidebarMenuBadge>{item.badge}</SidebarMenuBadge> : null}
    </SidebarMenuItem>
  );
}
